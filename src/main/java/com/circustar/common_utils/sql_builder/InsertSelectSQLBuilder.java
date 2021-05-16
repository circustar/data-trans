package com.circustar.common_utils.sql_builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class InsertSelectSQLBuilder implements ISQLBuilder {
    private String insertTable;
    private List<String> insertColumnList = new ArrayList<>();
    private ISQLBuilder selectSqlBuilder;

    public String getSql() {
        String selectSql = selectSqlBuilder.getSql();
        boolean containSelect = selectSql.contains("select");
        if(!containSelect) {
            selectSql = " select * from " + containSelect;
        }
        return "Insert into " + insertTable
                + "(" + insertColumnList.stream().collect(Collectors.joining(",")) + ")"
                + selectSql;
    }
}
