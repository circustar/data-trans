package com.circustar.data_trans.sql.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SetStatement implements ISQLBuilder {

    private Map<String, String> columnValueMap = new HashMap<>();

    @Override
    public String getSql() {
        String result = "";
        for(String key :columnValueMap.keySet()) {
            result = "," + key + " = " + columnValueMap.get(key);
        }
        if(!StringUtils.isEmpty(result)) {
            result = result.substring(1);
        }
        return result;
    }

}
