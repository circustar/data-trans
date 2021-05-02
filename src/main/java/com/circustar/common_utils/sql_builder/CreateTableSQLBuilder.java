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
public class CreateTableSQLBuilder implements ISQLBuilder {
    private String tableName;
    private List<TableColumnProperty> ColumnInfoList = new ArrayList<>();

    public String getSql() {
        return "create table " + tableName
                + "(" + ColumnInfoList.stream().map(x -> x.getColumnName() + " "
                + x.getColumnTypeWithLength() + (x.isNullable()?"": " not null "))
                .collect(Collectors.joining(","))
                + ")";
    }
}
