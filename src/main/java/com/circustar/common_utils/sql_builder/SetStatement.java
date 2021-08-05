package com.circustar.common_utils.sql_builder;

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
        StringBuffer result = new StringBuffer();
        for(Map.Entry<String, String> entry :columnValueMap.entrySet()) {
            result.append("," + entry.getKey() + " = " + entry.getValue());
        }
        if(StringUtils.hasLength(result)) {
            return result.substring(1);
        }
        return result.toString();
    }

}
