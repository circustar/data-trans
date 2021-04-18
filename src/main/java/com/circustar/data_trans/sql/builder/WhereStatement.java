package com.circustar.data_trans.sql.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class WhereStatement implements ISQLBuilder {
    private AndOrType andOrType = AndOrType.NONE;

    private String sqlWhere;

    private WhereStatement subWhereStatement;

    private WhereStatement nextWhereStatement;

    public WhereStatement(String sqlWhere) {
        this.sqlWhere = sqlWhere;
    }

    public WhereStatement(ISQLBuilder sqlWhereBuilder) {
        this.sqlWhere = sqlWhereBuilder.getSql();
    }

    public void addNextWhereStatement(WhereStatement nextWhereStatement,AndOrType andOrType) {
        this.nextWhereStatement = nextWhereStatement;
        this.nextWhereStatement.setAndOrType(andOrType);
    }

    public void addSubWhereStatement(WhereStatement subWhereStatement,AndOrType andOrType) {
        this.nextWhereStatement = nextWhereStatement;
        this.nextWhereStatement.setAndOrType(andOrType);
    }

    @Override
    public String getSql() {
        String result = sqlWhere;
        if(subWhereStatement != null) {
            result = result + subWhereStatement.andOrType.getLogicType() + " ("
                    + subWhereStatement.getSql() + ")";
        }
        if(nextWhereStatement != null) {
            result = result + nextWhereStatement.andOrType.getLogicType() + subWhereStatement.getSql() ;
        }
        return result;
    }

    public enum AndOrType{
        AND(" and "),
        OR(" or "),
        NONE("");

        private String logicType;

        AndOrType(String logicType) {
            this.logicType = logicType;
        }

        public String getLogicType() {
            return this.logicType;
        }
    }
}
