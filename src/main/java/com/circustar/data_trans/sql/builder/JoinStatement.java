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
public class JoinStatement implements ISQLBuilder {
    private ISQLBuilder selectSQLBuilder;

    private String joinTableAlias;

    private String joinType;

    private String onStatement;

    @Override
    public String getSql() {
        String jointTableString = selectSQLBuilder.getSql().toLowerCase();
        boolean containSubSql = jointTableString.contains("select")?true:false;
        if(containSubSql) {
            jointTableString = "(" + jointTableString + ")";
        }
        String result = " " + joinType + " " + jointTableString + " " + joinTableAlias;
        if(!StringUtils.isEmpty(onStatement)) {
            result +=  " ON " + onStatement;
        }

        return result;
    }
//
//    public enum JoinType{
//        LEFT_JOIN(" LEFT JOIN "),
//        RIGHT_JOIN(" RIGHT JOIN "),
//        INNER_JOIN(" INNER JOIN "),
//        FULL_JOIN(" FULL JOIN "),
//        COMMA_JOIN(",");
//
//        private String joinString;
//
//        JoinType(String joinString) {
//            this.joinString = joinString;
//        }
//
//        public String getJoinString() {
//            return this.joinString;
//        }
//    }
}
