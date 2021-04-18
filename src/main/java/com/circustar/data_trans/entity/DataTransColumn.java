package com.circustar.data_trans.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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

    private String indexName;

    private Integer indexOrder;
}
