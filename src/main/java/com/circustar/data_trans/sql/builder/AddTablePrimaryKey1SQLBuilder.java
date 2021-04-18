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
public class AddTablePrimaryKey1SQLBuilder implements ISQLBuilder {
    private String tableName;
    private String primaryKeyName;
    private List<String> columnInfoList = new ArrayList<>();

    public String getSql() {
        return "alter table " + tableName + " add primary_key " + primaryKeyName
                + columnInfoList.stream().collect(Collectors.joining(",", "(", ")"));
    }
}
