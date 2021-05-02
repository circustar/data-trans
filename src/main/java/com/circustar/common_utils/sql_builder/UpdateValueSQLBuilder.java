package com.circustar.common_utils.sql_builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UpdateValueSQLBuilder implements ISQLBuilder {
    private String updateTable;
    private Map<String, String> columnNameValueMap;
    private WhereStatement whereStatement;

    public String getSql() {
        String updateSql = "UPDATE " + updateTable  + " SET "
        + columnNameValueMap.entrySet().stream().map(x -> x.getKey() + " = " + x.getValue()).collect(Collectors.joining(","))
                + " where " + whereStatement.getSql();

        return updateSql;
    }
}
