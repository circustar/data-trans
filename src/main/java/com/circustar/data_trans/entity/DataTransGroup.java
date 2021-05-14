package com.circustar.data_trans.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DataTransGroup implements Serializable {
    @TableId
    private String dataTransGroupName;

    private Integer recoverable;

    private String remark;
}
