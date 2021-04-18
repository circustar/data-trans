package com.circustar.data_trans.sql.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TruncateTableSQLBuilder implements ISQLBuilder {
    private String tableName;

    public String getSql() {
        return "truncate table " + tableName;
    }
}
