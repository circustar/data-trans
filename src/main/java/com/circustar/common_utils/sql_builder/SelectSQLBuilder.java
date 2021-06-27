package com.circustar.common_utils.sql_builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class SelectSQLBuilder implements ISQLBuilder {
    private ISQLBuilder mainTable;
    private String mainTableAlias;
    private List<String> selectColumns;
    private List<JoinStatement> joinStatementList;
    private WhereStatement whereStatement;
    private List<String> groupColumns = new ArrayList<>();
    private HavingStatement havingStatement;
    private String prefix = "";
    private String suffix = "";
    public String getSql() {
        String mainTableString = mainTable.getSql();
        boolean containSubSql = mainTableString.contains("select")?true:false;
        if(containSubSql) {
            mainTableString = "(" + mainTableString + ")";
        }
        String result = "select " + (prefix == null?"":prefix) + " " + selectColumns.stream().collect(Collectors.joining(",")) + " from "
                + mainTableString + " " + mainTableAlias
                + joinStatementList.stream().map(x -> x.getSql()).collect(Collectors.joining(" "));
        if(whereStatement != null) {
            result += " where " + whereStatement.getSql();
        }
        if(groupColumns != null && groupColumns.size() > 0) {
            result += " group by " + groupColumns.stream().filter(x -> StringUtils.hasLength(x))
                    .collect(Collectors.joining(" , "));
        }
        if(havingStatement != null) {
            result += " having " + havingStatement.getSql();
        }
        result += " " + (suffix == null?"":suffix);

        return result;
    }
}
