package com.circustar.data_trans.sql.builder;

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
    private String updateTableAlias;
    private Map<String, String> columnNameValueMap;
    private WhereStatement whereStatement;

    public String getSql() {
        String updateSql = "UPDATE " + updateTable + " " + updateTableAlias + " SET "
        + columnNameValueMap.entrySet().stream().map(x -> x.getKey() + " = " + x.getValue()).collect(Collectors.joining(","))
                + " where " + whereStatement.getSql();

        return updateSql;
    }
}
