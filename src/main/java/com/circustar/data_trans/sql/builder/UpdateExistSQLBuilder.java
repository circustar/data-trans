package com.circustar.data_trans.sql.builder;

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
public class UpdateExistSQLBuilder implements ISQLBuilder {
    private String updateTable;
    private List<String> updateColumnList = new ArrayList<>();
    private ISQLBuilder selectSQLBuilder;
    private ISQLBuilder existSQLBuilder;

    public String getSql() {
        String updateSql = "UPDATE " + updateTable + " SET "
                + updateColumnList.stream().collect(Collectors.joining(",", "(", ")"))
                + " = (" + selectSQLBuilder.getSql() + ")"
                + " where exists(" + existSQLBuilder.getSql() + ")";
        return updateSql;
    }
}
