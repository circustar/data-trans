package com.circustar.data_trans.executor;

import com.circustar.common_utils.executor.IExecutor;
import com.circustar.common_utils.parser.SPELParser;

import java.sql.Connection;
import java.util.Map;

public interface IDataTransSqlExecutor extends IExecutor<Map<String, Object>> {
    String EXEC_CONNECTION = "_DATA_TRANS_CONNECTION";
    String EXEC_PARAM_AND_VALUE = "_DATA_TRANS_PARAM_AND_VALUE";
    String EXEC_EXECUTED_DATA_TRANS_LIST = "_DATA_TRANS_EXECUTED_LIST";
    String EXEC_ID = "_DATA_TRANS_EXEC_ID";

    @Override
    default void process(Map<String, Object> param){
        Connection connection = (Connection) param.get(EXEC_CONNECTION);
        Map<String, String> paramAndValue = (Map<String, String>) param.get(EXEC_PARAM_AND_VALUE);
        String executeSql = SPELParser.parseStringExpression(paramAndValue, getSql());
        try {
            beforeExecute(connection, executeSql, param);
            execSQL(connection, executeSql, param);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    String getSql();
    void setSql(String sql);
    void beforeExecute(Connection connection , String sql, Map<String, Object> param) throws Exception;
    void execSQL(Connection connection , String sql, Map<String, Object> param) throws Exception;
}
