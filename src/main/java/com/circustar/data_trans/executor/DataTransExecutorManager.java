package com.circustar.data_trans.executor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.circustar.common_utils.executor.*;
import com.circustar.common_utils.parser.SPELParser;
import com.circustar.data_trans.common.Constant;
import com.circustar.data_trans.entity.*;
import com.circustar.data_trans.service.*;
import com.circustar.data_trans.executor.init.DataTransInitExecutorBuilder;
import com.circustar.data_trans.executor.init.DataTransTableDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.*;
import java.util.function.Predicate;
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

    private IExecutor<Map<String, Object>> buildExecutor(DataTransGroup dataTransGroup) {
        if (groupNameExecutorMap.containsKey(dataTransGroup.getDataTransGroupName())) {
            return groupNameExecutorMap.get(dataTransGroup.getDataTransGroupName());
        }
        QueryWrapper<DataTrans> qw = new QueryWrapper<>();
        qw.eq(DataTransTableDefinition.COLUMN_NAME_DATA_TRANS_GROUP_NAME, dataTransGroup.getDataTransGroupName());
        qw.orderByAsc(DataTransTableDefinition.COLUMN_NAME_DATA_TRANS_ID);
        List<DataTrans> oriDataTransList = dataTransService.list(qw).stream()
                .filter(x -> !Constant.CONST_YES.equals(x.getDisabled())).collect(Collectors.toList());

        IExecutor<Map<String, Object>> result = createExecutors(oriDataTransList, x -> !StringUtils.hasLength(x.getDependDataTransId()));

        tryPutMap(dataTransGroup.getDataTransGroupName(), result);
        return result;
    }

    private IExecutor<Map<String, Object>> createExecutors(List<DataTrans> oriDataTransList, Predicate<DataTrans> predicate) {

        List<DataTrans> topDataTrans = oriDataTransList.stream().filter(x ->
                predicate.test(x))
                .sorted(Comparator.comparing(DataTrans::getDataTransId))
                .collect(Collectors.toList());
        if(topDataTrans == null || topDataTrans.size() == 0) {
            return null;
        }

        BaseListExecutor<Map<String, Object>> result = new BaseListExecutor<>();
        for(DataTrans dataTrans : topDataTrans) {
            BaseLinkedExecutor<Map<String, Object>> nodeExecutor = new BaseLinkedExecutor<>(createExecutor(dataTrans));
            IExecutor<Map<String, Object>> subExecutor = createExecutors(oriDataTransList, x -> dataTrans.getDataTransId().equals(x.getDependDataTransId()));
            if(subExecutor != null) {
                nodeExecutor.setNextExecutor(subExecutor);
            }
            result.addExecutor(nodeExecutor);
        }

        return result;
    }

    private IExecutor<Map<String, Object>> createExecutor(DataTrans dataTrans) {
        QueryWrapper qw2 = new QueryWrapper<>();
        qw2.eq(DataTransTableDefinition.COLUMN_NAME_DATA_TRANS_ID, dataTrans.getDataTransId());
        List<DataTransSource> dataTransSourceList = dataTransSourceService.list(qw2);
        List<DataTransColumn> dataTransColumnList = dataTransColumnService.list(qw2);
        DataTransExecutorBuilder dataTransExecutorBuilder = new DataTransExecutorBuilder(
                dataTrans, dataTransSourceList, dataTransColumnList);
        IExecutor<Map<String, Object>> executor = dataTransExecutorBuilder.build();
        ISkipExecutor<Map<String, Object>> skipExecutor = new BaseSkipExecutor<Map<String, Object>>()
                .setExecutor(executor)
                .setSkipExpression(param -> {
                    HashSet<String> executedList = (HashSet<String>) param.get(IDataTransSqlExecutor.EXEC_EXECUTED_DATA_TRANS_LIST);
                    if(executedList.contains(dataTrans.getDataTransId())) {
                        return true;
                    }
                    if(!StringUtils.hasLength(dataTrans.getSkipExpression())) {
                        return false;
                    }
                    String skipExpression = SPELParser.parseExpression(param.get(IDataTransSqlExecutor.EXEC_PARAM_AND_VALUE)
                            , dataTrans.getSkipExpression()).toString().trim();
                    if(!StringUtils.hasLength(skipExpression)) {return false;}
                    Boolean skipResult = SPELParser.calcExpression(skipExpression, Boolean.class);
                    return skipResult == null? false : skipResult;
                });
        skipExecutor.addAfterExecuteConsumer(param -> {
            Long execId = (Long) param.get(IDataTransSqlExecutor.EXEC_ID);
            dataTransExecStepService.updateExecResult(execId, dataTrans.getDataTransId(), Constant.CONST_NO);
        });

        skipExecutor.setExecuteErrorConsumer((param, e) -> {
            Long execId = (Long) param.get(IDataTransSqlExecutor.EXEC_ID);
            dataTransExecStepService.updateExecResult(execId, dataTrans.getDataTransId(), Constant.CONST_YES);
        });
        return skipExecutor;
    }

    public void execInit() throws Exception {
        try(Connection connection = dataSource.getConnection()) {
            DataTransInitExecutorBuilder dataTransInitExecutorBuilder = new DataTransInitExecutorBuilder();
            IExecutor<Map<String, Object>> initExecutor = dataTransInitExecutorBuilder.build();
            Map<String, Object> execParam = new HashMap<>();
            execParam.put(IDataTransSqlExecutor.EXEC_CONNECTION, connection);
            execParam.put(IDataTransSqlExecutor.EXEC_PARAM_AND_VALUE, new HashMap<>());
            initExecutor.execute(execParam);
        }
    }

    public void exec(Long execId) {
        exec(execId, false);
    }

    public void exec(Long execId, boolean refreshCache) {
        DataTransExec dataTransExec = dataTransExecService.getById(execId);
        DataTransGroup dataTransGroup = dataTransGroupService.getById(dataTransExec.getDataTransGroupName());
        if(refreshCache) {
            tryRemoveMap(dataTransGroup.getDataTransGroupName());
        }
        IExecutor<Map<String, Object>> executor = buildExecutor(dataTransGroup);
        QueryWrapper qw = new QueryWrapper();
        qw.eq(DataTransTableDefinition.COLUMN_NAME_DATA_TRANS_EXEC_ID, execId);
        List<DataTransExecParam> dataTransExecParams = dataTransExecParamService.list(qw);
        Map<String, String> execParam = dataTransExecParams.stream().collect(Collectors.toMap(DataTransExecParam::getParamName
                , DataTransExecParam::getParamValue));
        Map<String, Object> param = new HashMap<>();
        Set<String> executedDataTransSet = new HashSet();
        if(Constant.CONST_YES.equals(dataTransGroup.getRecoverable())) {
            QueryWrapper<DataTransExecStep> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(DataTransTableDefinition.COLUMN_NAME_DATA_TRANS_EXEC_ID, execId);
            executedDataTransSet = dataTransExecStepService.list(queryWrapper)
                    .stream().filter(x -> Constant.CONST_YES.equals(x.getExecuted()) && Constant.CONST_NO.equals(x.getExecuteError()))
                    .map(x -> x.getDataTransId())
                    .collect(Collectors.toSet());
        }
        try(Connection connection = dataSource.getConnection()) {
            param.put(IDataTransSqlExecutor.EXEC_CONNECTION, connection);
            param.put(IDataTransSqlExecutor.EXEC_PARAM_AND_VALUE, execParam);
            param.put(IDataTransSqlExecutor.EXEC_EXECUTED_DATA_TRANS_LIST, executedDataTransSet);
            param.put(IDataTransSqlExecutor.EXEC_ID, execId);
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
