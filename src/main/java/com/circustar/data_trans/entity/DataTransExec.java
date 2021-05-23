package com.circustar.data_trans.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DataTransExec implements Serializable {
    @TableId
    private Long dataTransExecId;

    private String dataTransGroupName;

    private Integer executed;

    private Integer executeError;

    private Date executeTime;

    private String message;
}
