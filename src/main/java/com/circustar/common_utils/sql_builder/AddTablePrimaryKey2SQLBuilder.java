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
public class AddTablePrimaryKey2SQLBuilder implements ISQLBuilder {
    private String tableName;
    private String primaryKeyName;
    private List<String> columnInfoList = new ArrayList<>();

    public String getSql() {
        return "alter table " + tableName + " add constraint " + primaryKeyName
                + " primary key " + columnInfoList.stream().collect(Collectors.joining(",", "(", ")"));
    }
}
