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
public class AddTableIndexSQLBuilder implements ISQLBuilder {
    private String tableName;
    private String indexName;
    private boolean isUnique = false;
    private List<String> columnInfoList = new ArrayList<>();

    public String getSql() {
        return "create " + (isUnique?"UNIQUE ":"") + "index " + indexName + " on " + tableName
                + columnInfoList.stream().collect(Collectors.joining(",", "(", ")"));
    }
}
