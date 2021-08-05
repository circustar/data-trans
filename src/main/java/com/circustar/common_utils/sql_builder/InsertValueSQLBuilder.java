package com.circustar.common_utils.sql_builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class InsertValueSQLBuilder implements ISQLBuilder {
    private String insertTable;
    private Map<String, String> columnNameValueMap = new HashMap<>();

    public String getSql() {
        StringBuffer insertColumns = new StringBuffer();
        StringBuffer insertValues = new StringBuffer();
        Set<Map.Entry<String, String>> entries = columnNameValueMap.entrySet();
        for(Map.Entry<String, String> entry : entries) {
            insertColumns.append("," + entry.getKey());
            insertValues.append("," + entry.getValue());
        }
        return "Insert into " + insertTable
                + "(" + insertColumns.substring(1) + ") values ("
                + insertValues.substring(1) + ")" ;
    }
}
