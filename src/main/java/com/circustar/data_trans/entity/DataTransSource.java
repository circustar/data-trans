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
public class DataTransSource implements Serializable {
    @TableId
    private String dataTransSourceId;

    private String dataTransId;

    private String sourceTable;

    private String alias;

    private String joinType;

    private String onStatement;

    private String whereStatement;
}
