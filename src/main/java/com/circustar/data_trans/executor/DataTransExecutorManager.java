package com.circustar.data_trans.executor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.circustar.common_utils.executor.*;
import com.circustar.data_trans.common.Constant;
import com.circustar.data_trans.entity.*;
import com.circustar.data_trans.service.*;
import com.circustar.data_trans.executor.init.DataTransInitExecutorBuilder;
import com.circustar.data_trans.executor.init.DataTransTableDefinition;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class DataTransExecutorManager {
    private DataSource dataSource;

    private IDataTransGroupService dataTransGroupService;

    private IDataTransService dataTransService;

    private IDataTransSourceService dataTransSourceService;

    private IDataTransColumnService dataTransColumnService;

    private IDataTransExecService dataTransExecService;

    private IDataTransExecStepService dataTransExecStepService;

    private IDataTransExecParamService dataTransExecParamService;

    private static Map<String, IExecutor<Map<String, Object>>> groupNameExecutorMap = new HashMap<>();

    private static void tryPutMap(String groupName, IExecutor<Map<String, Object>> listExecutor) {
        try {
            groupNameExecutorMap.put(groupName, listExecutor);
        } catch (Exception ex) {
        }
    }

    private static void tryRemoveMap(String groupName) {
        try {
            groupNameExecutorMap.remove(groupName);
        } catch (Exception ex) {
        }
    }

    public DataTransExecutorManager(DataSource dataSource
            , IDataTransGroupService dataTransGroupService
            , IDataTransService dataTransService
            , IDataTransSourceService dataTransSourceService
            , IDataTransColumnService dataTransColumnService
            , IDataTransExecService dataTransExecService
            , IDataTransExecStepService dataTransExecStepService
            , IDataTransExecParamService dataTransExecParamService) {
        this.dataSource = dataSource;
        this.dataTransGroupService = dataTransGroupService;
        this.dataTransService = dataTransService;
        this.dataTransSourceService = dataTransSourceService;
        this.dataTransColumnService = dataTransColumnService;
        this.dataTransExecService = dataTransExecService;
        this.dataTransExecStepService = dataTransExecStepService;
        this.dataTransExecParamService = dataTransExecParamService;
    }

    public IExecutor<Map<String, Object>> buildExecutor(DataTransGroup dataTransGroup, DataTransExec dataTransExec) {
        Map<String, DataTransExecStep> dataTransExecStepMap = new HashMap<>();
        boolean findError = false;
        if(dataTransGroup.getRecoverable() == Constant.CONST_YES) {
            QueryWrapper<DataTransExecStep> qwStep = new QueryWrapper<>();
            qwStep.eq(DataTransTableDefinition.COLUMN_NAME_DATA_TRANS_EXEC_ID, dataTransExec.getDataTransExecId());
            dataTransExecStepMap = dataTransExecStepService.list(qwStep)
                    .stream().filter(x -> x.getExecuteError() == Constant.CONST_YES)
                    .collect(Collectors.toMap(x -> x.getDataTransId(), y -> y));
            findError = !dataTransExecStepMap.keySet().isEmpty();
        }

        if(!findError) {
            if (groupNameExecutorMap.containsKey(dataTransGroup.getDataTransGroupName())) {
                return groupNameExecutorMap.get(dataTransGroup.getDataTransGroupName());
            }
        }
        QueryWrapper<DataTrans> qw = new QueryWrapper<>();
        qw.eq(DataTransTableDefinition.COLUMN_NAME_DATA_TRANS_GROUP_NAME, dataTransGroup.getDataTransGroupName());
        qw.orderByAsc(DataTransTableDefinition.COLUMN_NAME_DATA_TRANS_ID);
        List<DataTrans> dataTransList = dataTransService.list(qw);

        BaseListExecutor<Map<String, Object>> result = new BaseListExecutor<>();
        boolean checkErrorMap = findError;
        for(DataTrans dataTrans : dataTransList) {
            if(checkErrorMap) {
                if(dataTransExecStepMap.containsKey(dataTrans.getDataTransId())) {
                    checkErrorMap = false;
                } else {
                    continue;
                }
            }
            QueryWrapper qw2 = new QueryWrapper<>();
            qw2.eq(DataTransTableDefinition.COLUMN_NAME_DATA_TRANS_ID, dataTrans.getDataTransId());
            List<DataTransSource> dataTransSourceList = dataTransSourceService.list(qw2);
            List<DataTransColumn> dataTransColumnList = dataTransColumnService.list(qw2);
            DataTransExecutorBuilder dataTransExecutorBuilder = new DataTransExecutorBuilder(
                    dataTrans, dataTransSourceList, dataTransColumnList);
            IExecutor<Map<String, Object>> executor = dataTransExecutorBuilder.build();
            executor.addAfterExecuteConsumer(param -> {
                dataTransExecStepService.updateExecResult(dataTransExec.getDataTransExecId(), dataTrans.getDataTransId(), Constant.CONST_NO);
            });

            executor.setExecuteErrorConsumer((param, e) -> {
                dataTransExecStepService.updateExecResult(dataTransExec.getDataTransExecId(), dataTrans.getDataTransId(), Constant.CONST_YES);
            });

            result.addExecutor(executor);
        }
        if(!findError) {
            tryPutMap(dataTransGroup.getDataTransGroupName(), result);
        }
        return result;
    }

    public void execInit() throws Exception {
        try(Connection connection = dataSource.getConnection()) {
            DataTransInitExecutorBuilder dataTransInitExecutorBuilder = new DataTransInitExecutorBuilder();
            IExecutor<Map<String, Object>> initExecutor = dataTransInitExecutorBuilder.build();
            Map<String, Object> execParam = new HashMap<>();
            execParam.put(BaseSqlExecutor.EXEC_CONNECTION, connection);
            execParam.put(BaseSqlExecutor.EXEC_PARAM_AND_VALUE, new HashMap<>());
            initExecutor.execute(execParam);
        }
    }

    public void exec(int execId) throws Exception {
        DataTransExec dataTransExec = dataTransExecService.getById(execId);
        DataTransGroup dataTransGroup = dataTransGroupService.getById(dataTransExec.getDataTransGroupName());
        IExecutor<Map<String, Object>> executor = buildExecutor(dataTransGroup, dataTransExec);
        QueryWrapper qw = new QueryWrapper();
        qw.eq(DataTransTableDefinition.COLUMN_NAME_DATA_TRANS_EXEC_ID, execId);
        List<DataTransExecParam> dataTransExecParams = dataTransExecParamService.list(qw);
        Map<String, String> execParam = dataTransExecParams.stream().collect(Collectors.toMap(DataTransExecParam::getParamName
                , DataTransExecParam::getParamValue));
        Map<String, Object> param = new HashMap<>();
        try(Connection connection = dataSource.getConnection()) {
            param.put(BaseSqlExecutor.EXEC_CONNECTION, connection);
            param.put(BaseSqlExecutor.EXEC_PARAM_AND_VALUE, execParam);
            executor.execute(param);
            dataTransExec.setExecuteError(Constant.CONST_NO);
            dataTransExec.setMessage("");
        } catch (Exception ex) {
            dataTransExec.setExecuteError(Constant.CONST_YES);
            String message = Optional.ofNullable(ex.getMessage()).orElse("unknown exception");
            log.error(message);
            dataTransExec.setMessage(message.length() > 1000 ? message.substring(0, 1000) : message);
            tryRemoveMap(dataTransGroup.getDataTransGroupName());
        }
        dataTransExec.setExecuted(1);
        dataTransExec.setExecuteTime(new Date());
        dataTransExecService.updateById(dataTransExec);
    }
}
