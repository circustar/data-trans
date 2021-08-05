package com.circustar.data_trans.executor;

import com.circustar.common_utils.executor.AbstractExecutor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;

@Slf4j
public class BaseDataTransSqlExecutor extends AbstractExecutor<Map<String, Object>> implements IDataTransSqlExecutor {
    private String sql;
    public BaseDataTransSqlExecutor(String sql) {
        this.sql = sql;
    }

    @Override
    public String getSql() {
        return sql;
    }
    @Override
    public void setSql(String sql) {
        this.sql = sql;
    }
    @Override
    public void beforeExecute(Connection connection , String sql, Map<String, Object> param) throws Exception {
        log.info("EXECUTE SQL:" + sql);
    }
    @Override
    public void execSQL(Connection connection , String sql, Map<String, Object> param) throws Exception {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(sql);
        } finally {
            if(statement != null) {
                statement.close();
            }
        }
    }
}
