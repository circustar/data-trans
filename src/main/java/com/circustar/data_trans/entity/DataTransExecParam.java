package com.circustar.data_trans.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class DataTransExecParam implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private String DataTransExecParamId;

    private String DataTransExecId;

    private String paramName;

    private String paramValue;

}
