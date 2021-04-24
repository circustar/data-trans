package com.circustar.data_trans.sql.executor;

import com.circustar.common_utils.parser.SPELParser;
import com.circustar.common_utils.executor.AbstractExecutor;
import com.circustar.common_utils.executor.IExecutor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

@Slf4j
public class BaseSqlExecutor extends AbstractExecutor<Map<String, Object>> implements IExecutor<Map<String, Object>> {
    public final static String EXEC_CONNECTION = "P_CONNECTION";
    public final static String EXEC_PARAM_AND_VALUE = "P_PARAM_AND_VALUE";
    private String sql;
    public BaseSqlExecutor(String sql) {
        this.sql = sql;
    }

    @Override
    public void execute(Map<String, Object> param) throws SQLException {
        Connection connection = (Connection) param.get(EXEC_CONNECTION);
        Map<String, String> paramAndValue = (Map<String, String>) param.get(EXEC_PARAM_AND_VALUE);
        String executeSql = sql;
        if(paramAndValue != null) {
            executeSql = SPELParser.parseExpression(paramAndValue, sql).toString();
        }
        log.info(executeSql);
        Statement statement = connection.createStatement();
        statement.execute(executeSql);
    }
}
