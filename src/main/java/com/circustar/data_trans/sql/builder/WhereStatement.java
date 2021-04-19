package com.circustar.data_trans.sql.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class WhereStatement implements ISQLBuilder {
    private LogicType logicType = LogicType.EMPTY;

    private String sqlWhere;

    private WhereStatement nextWhereStatement;

    public WhereStatement(String sqlWhere) {
        this.sqlWhere = sqlWhere;
    }

    public WhereStatement(ISQLBuilder sqlWhereBuilder) {
        this.sqlWhere = sqlWhereBuilder.getSql();
    }

    public void addNextWhereStatement(WhereStatement nextWhereStatement, LogicType logicType) {
        WhereStatement lastWhereStatement = this;
        while(lastWhereStatement.nextWhereStatement != null) {
            lastWhereStatement = lastWhereStatement.nextWhereStatement;
        }
        lastWhereStatement.nextWhereStatement = nextWhereStatement;
        nextWhereStatement.setLogicType(logicType);
    }

    @Override
    public String getSql() {
        String result = sqlWhere;
        if(nextWhereStatement != null) {
            result = result + (StringUtils.isEmpty(result)?"" : nextWhereStatement.logicType.getLogicType())
                    + "(" + nextWhereStatement.getSql() + ")" ;
        }
        return result;
    }

    public enum LogicType {
        AND(" and "),
        AND_NOT(" and not "),
        OR(" or "),
        OR_NOT(" or not "),
        NOT(" not "),
        EMPTY("");

        private String logicType;

        LogicType(String logicType) {
            this.logicType = logicType;
        }

        public String getLogicType() {
            return this.logicType;
        }
    }
}
