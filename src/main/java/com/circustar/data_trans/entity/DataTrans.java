package com.circustar.data_trans.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.circustar.data_trans.common.ColumnIndexInfo;
import com.circustar.data_trans.common.TableIndexInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DataTrans implements Serializable {
    @TableId
    private String dataTransId;

    private String dataTransGroupName;

    private String dependDataTransId;

    private String tableName;

    @TableLogic
    private Integer disabled;

    private Integer dropTableFlag;

    private Integer createTableFlag;

    private Integer truncateTableFlag;

    private String updateType;

    private String selectPrefix;

    private String selectSuffix;

    private String skipExpression;

    private Integer addToParamMap;

    private String remark;

    @TableField(exist = false)
    private List<TableIndexInfo> tableIndexInfoList;

    public void initTableIndexInfo(List<ColumnIndexInfo> columnIndexInfos) {
        if(CollectionUtils.isEmpty(columnIndexInfos)) {
            return;
        }
        final List<String> allIndexNames = columnIndexInfos.stream().map(x -> x.getIndexName())
                .distinct().collect(Collectors.toList());
        List<TableIndexInfo> result = new ArrayList<>();
        for(String indexName : allIndexNames) {
            TableIndexInfo tableIndexInfo = new TableIndexInfo();
            tableIndexInfo.setIndexName(indexName);
            tableIndexInfo.setColumnNameList(columnIndexInfos.stream().filter(x -> indexName.equals(x.getIndexName()))
                    .sorted(Comparator.comparingInt(ColumnIndexInfo::getIndexOrder))
                    .map(x -> x.getColumnName()).collect(Collectors.toList()));
            result.add(tableIndexInfo);
        }
        this.tableIndexInfoList = result;
    }

}
