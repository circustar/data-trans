package com.circustar.data_trans.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.circustar.data_trans.common.ColumnIndexInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DataTransColumn implements Serializable {
    @TableId
    private String dataTransColumnId;

    private String dataTransId;

    private String columnName;

    private String columnType;

    private String columnValue;

    private Integer groupFlag;

    private Integer primaryKey;

    private String indexNameOrders;

    @TableField(exist=false)
    private List<ColumnIndexInfo> columnIndexInfoList;

    public DataTransColumn initTableIndexInfoList() {
        if(!StringUtils.hasLength(indexNameOrders)) {
            return null;
        }
        this.columnIndexInfoList = Arrays.stream(indexNameOrders.split(";")).map(x -> x.trim())
                .filter(x -> StringUtils.hasLength(x))
                .map(x -> {
                    ColumnIndexInfo columnIndexInfo = new ColumnIndexInfo();
                    final String[] split = x.split(",");
                    columnIndexInfo.setColumnName(columnName);
                    columnIndexInfo.setIndexName(split[0].trim().toLowerCase());
                    if(split.length > 1) {
                        columnIndexInfo.setIndexOrder(Integer.parseInt(split[1]));
                    } else {
                        columnIndexInfo.setIndexOrder(0);
                    }
                    return columnIndexInfo;
                }).collect(Collectors.toList());
        return this;
    }
}
