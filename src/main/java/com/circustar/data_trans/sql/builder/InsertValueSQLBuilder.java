package com.circustar.data_trans.sql.builder;

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
        String insertColumns = "";
        String insertValues = "";
        Set<Map.Entry<String, String>> entries = columnNameValueMap.entrySet();
        for(Map.Entry<String, String> entry : entries) {
            insertColumns += "," + entry.getKey();
            insertValues += "," + entry.getValue();
        }
        return "Insert into " + insertTable
                + "(" + insertColumns.substring(1) + ") values ("
                + insertValues.substring(1) + ")" ;
    }
}
