package com.circustar.data_trans.sql.executor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.circustar.data_trans.entity.*;
import com.circustar.data_trans.service.impl.*;
import com.circustar.data_trans.sql.init.DataTransInitExecutorBuilder;
import com.circustar.data_trans.sql.init.DataTransTableDefinition;
import com.circustar.common_utils.executor.BaseListExecutor;
import com.circustar.common_utils.executor.IExecutor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataTransExecutorManager {
    private DataSource dataSource;

    private DataTransGroupService dataTransGroupService;

    private DataTransService dataTransService;

    private DataTransSourceService dataTransSourceService;

    private DataTransColumnService dataTransColumnService;

    private DataTransExecService dataTransExecService;

    private DataTransExecParamService dataTransExecParamService;

    private static Map<String, IExecutor<Map<String, Object>>> groupNameExecutorMap = new HashMap<>();

    private static void tryPutMap(String groupName, IExecutor<Map<String, Object>> listExecutor) {
        try {
            groupNameExecutorMap.put(groupName, listExecutor);
        } catch (Exception ex) {
        }
    }

    public DataTransExecutorManager(DataSource dataSource
            , DataTransGroupService dataTransGroupService
            , DataTransService dataTransService
            , DataTransSourceService dataTransSourceService
            , DataTransColumnService dataTransColumnService
            , DataTransExecService dataTransExecService
            , DataTransExecParamService dataTransExecParamService) {
        this.dataTransGroupService = dataTransGroupService;
        this.dataTransService = dataTransService;
        this.dataTransSourceService = dataTransSourceService;
        this.dataTransColumnService = dataTransColumnService;
        this.dataTransExecService = dataTransExecService;
        this.dataTransExecParamService = dataTransExecParamService;
    }

    public IExecutor<Map<String, Object>> getExecutor(String groupName) {
        if (groupNameExecutorMap.containsKey(groupName)) {
            return groupNameExecutorMap.get(groupName);
        }
        QueryWrapper<DataTrans> qw = new QueryWrapper<>();
        qw.eq(DataTransTableDefinition.COLUMN_NAME_DATA_TRANS_GROUP_NAME, groupName);
        qw.orderByAsc(DataTransTableDefinition.COLUMN_NAME_DATA_TRANS_ID);
        List<DataTrans> dataTransList = dataTransService.list(qw);
        BaseListExecutor<Map<String, Object>> result = new BaseListExecutor<>();
        for(DataTrans dataTrans : dataTransList) {
            QueryWrapper qw2 = new QueryWrapper<>();
            qw2.eq(DataTransTableDefinition.COLUMN_NAME_DATA_TRANS_ID, dataTrans.getDataTransId());
            List<DataTransSource> dataTransSourceList = dataTransSourceService.list(qw2);
            List<DataTransColumn> dataTransColumnList = dataTransColumnService.list(qw2);
            DataTransExecutorBuilder dataTransExecutorBuilder = new DataTransExecutorBuilder(
                    dataTrans, dataTransSourceList, dataTransColumnList);
            IExecutor<Map<String, Object>> executor = dataTransExecutorBuilder.build();
            result.addExecutor(executor);
        }
        tryPutMap(groupName, result);
        return result;
    }

    public void execInit(Connection connection) throws Exception {
        DataTransInitExecutorBuilder dataTransInitExecutorBuilder = new DataTransInitExecutorBuilder();
        IExecutor<Map<String, Object>> initExecutor = dataTransInitExecutorBuilder.build();
        Map<String, Object> execParam = new HashMap<>();
        execParam.put(BaseSqlExecutor.EXEC_CONNECTION, connection);
        execParam.put(BaseSqlExecutor.EXEC_PARAM_AND_VALUE, new HashMap<>());
        initExecutor.execute(execParam);
    }

    public void exec(int execId, Connection connection) throws Exception {
        DataTransExec dataTransExec = dataTransExecService.getById(execId);
        IExecutor<Map<String, Object>> executor = getExecutor(dataTransExec.getDataTransGroupName());
        QueryWrapper qw = new QueryWrapper();
        qw.eq(DataTransTableDefinition.COLUMN_NAME_DATA_TRANS_EXEC_ID, execId);
        List<DataTransExecParam> dataTransExecParams = dataTransExecParamService.list(qw);
        Map<String, String> execParam = dataTransExecParams.stream().collect(Collectors.toMap(DataTransExecParam::getParamName
                , DataTransExecParam::getParamValue));
        Map<String, Object> param = new HashMap<>();
        param.put(BaseSqlExecutor.EXEC_CONNECTION, connection);
        param.put(BaseSqlExecutor.EXEC_PARAM_AND_VALUE, execParam);
        try {
            executor.execute(param);
            dataTransExec.setExecuteError(0);
            dataTransExec.setMessage("");
        } catch (Exception ex) {
            dataTransExec.setExecuteError(1);
            dataTransExec.setMessage(ex.getMessage().length() > 1000?ex.getMessage().substring(0, 1000) : ex.getMessage());
        }
        dataTransExec.setExecuted(1);
        dataTransExec.setExecuteTime(new Date());
        dataTransExecService.updateById(dataTransExec);
    }
}
