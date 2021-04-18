package com.circustar.data_trans.sql.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DeleteExistSQLBuilder implements ISQLBuilder {
    private String deleteTable;
    private String deleteTableAlias;
    private ISQLBuilder selectSqlBuilder;

    public String getSql() {
        return "delete from " + deleteTable + " " +  deleteTableAlias
                + " where exists( " + selectSqlBuilder.getSql()  + ")";
    }
}
