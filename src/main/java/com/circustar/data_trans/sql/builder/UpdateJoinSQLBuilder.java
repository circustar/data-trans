package com.circustar.data_trans.sql.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UpdateJoinSQLBuilder implements ISQLBuilder {
    private String updateTable;
    private List<JoinStatement> joinStatements;
    private Map<String, String> columnNameValueMap;
    private WhereStatement whereStatement;

    public String getSql() {
        String joinString = "";
        if(joinStatements != null) {
            joinString = joinStatements.stream()
                    .map(x -> x.getSql())
                    .filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(""));
        }

        String updateSql = "UPDATE " + updateTable + " " + joinString  + " SET "
                + columnNameValueMap.entrySet().stream().map(x -> updateTable + "." + x.getKey() + " = " + x.getValue())
                .collect(Collectors.joining(","))
                + " where " + whereStatement.getSql();

        return updateSql;
    }
}
