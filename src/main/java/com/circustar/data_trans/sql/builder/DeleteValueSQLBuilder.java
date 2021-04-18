package com.circustar.data_trans.sql.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DeleteValueSQLBuilder implements ISQLBuilder {
    private String deleteTable;
    private WhereStatement whereStatement;

    public String getSql() {
        return "delete from " + deleteTable + " where " + whereStatement.getSql();
    }
}
