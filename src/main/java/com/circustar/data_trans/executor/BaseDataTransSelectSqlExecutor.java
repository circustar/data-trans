package com.circustar.data_trans.executor;

import com.circustar.common_utils.executor.AbstractExecutor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class BaseDataTransSelectSqlExecutor extends AbstractExecutor<Map<String, Object>> implements IDataTransSqlExecutor {
    private String sql;
    public BaseDataTransSelectSqlExecutor(String sql) {
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
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            Map<String, String> newMap = new HashMap<>();
            if (resultSet.next()) {
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    newMap.put(metaData.getColumnName(i), resultSet.getString(metaData.getColumnName(i)));
                }
            }
            param.putAll(newMap);
        } finally {
            if(resultSet != null) {
                resultSet.close();
            }
        }
    }
}
