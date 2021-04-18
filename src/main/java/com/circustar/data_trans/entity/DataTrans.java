package com.circustar.data_trans.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DataTrans implements Serializable {
    @TableId
    private String dataTransId;

    private String dataTransGroupName;

    private String tableName;

    @TableLogic
    private Integer disabled;

    private Integer dropTableFlag;

    private Integer createTableFlag;

    private Integer truncateTableFlag;

    private String updateType;

    private String remark;

}
