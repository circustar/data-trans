package com.circustar.data_trans.sql.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UpdateJoinSQLBuilder implements ISQLBuilder {
    private String updateTable;
    private String updateTableAlias;
    private List<JoinStatement> joinStatements;
    private Map<String, String> columnNameValueMap;
    private WhereStatement whereStatement;

    public String getSql() {
        String joinString = "";
        if(joinStatements != null) {
            joinString = joinStatements.stream().map(x -> x.getSql()).collect(Collectors.joining(" "));
        }

        String updateSql = "UPDATE " + updateTable + " " + joinString + updateTableAlias + " SET "
                + columnNameValueMap.entrySet().stream().map(x -> x.getKey() + " = " + x.getValue())
                .collect(Collectors.joining(","))
                + " where " + whereStatement.getSql();

        return updateSql;
    }
}
