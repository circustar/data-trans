package com.circustar.data_trans.sql.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SimpleSelectTableSQLBuilder implements ISQLBuilder {
    private String mainTable;
    private String mainTableAlias;
    public String getSql() {
        return mainTable + " " + mainTableAlias;
    }
}
