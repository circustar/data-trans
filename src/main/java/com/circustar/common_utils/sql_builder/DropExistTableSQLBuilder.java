package com.circustar.common_utils.sql_builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DropExistTableSQLBuilder implements ISQLBuilder {
    private String tableName;

    public String getSql() {
        return "drop table if exists " + tableName;
    }
}
