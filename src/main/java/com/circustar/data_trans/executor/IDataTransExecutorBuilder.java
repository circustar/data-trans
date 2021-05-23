package com.circustar.data_trans.executor;

import com.circustar.data_trans.common.Constant;
import com.circustar.data_trans.entity.DataTrans;
import com.circustar.data_trans.entity.DataTransColumn;
import com.circustar.data_trans.entity.DataTransSource;
import com.circustar.common_utils.executor.*;
import com.circustar.common_utils.sql_builder.*;
import com.circustar.data_trans.executor.init.DataTransTableDefinition;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public interface IDataTransExecutorBuilder {
    String UNIQUE_INDEX_PREFIX = "uni";
    default WhereStatement createWhereStatement(List<DataTransSource> dataTransSources) {
        WhereStatement topStatement = null;
        WhereStatement whereStatement = null;
        WhereStatement nextWhereStatement ;
        for(DataTransSource dataTransSource : dataTransSources) {
            if(StringUtils.isEmpty(dataTransSource.getWhereStatement())) {
                continue;
            }
            if(whereStatement == null) {
                whereStatement = new WhereStatement(dataTransSource.getWhereStatement());
                topStatement = whereStatement;
            } else {
                nextWhereStatement = new WhereStatement(dataTransSource.getWhereStatement());
                whereStatement.addNextWhereStatement(nextWhereStatement, WhereStatement.LogicType.AND);
                whereStatement = nextWhereStatement;
            }
        }
        return topStatement;
    }

    default WhereStatement createOnStatement(List<DataTransSource> dataTransSources) {
        WhereStatement topStatement = null;
        WhereStatement whereStatement = null;
        WhereStatement nextWhereStatement ;
        for(DataTransSource dataTransSource : dataTransSources) {
            if(StringUtils.isEmpty(dataTransSource.getOnStatement())) {
                continue;
            }
            if(whereStatement == null) {
                whereStatement = new WhereStatement(dataTransSource.getOnStatement());
                topStatement = whereStatement;
            } else {
                nextWhereStatement = new WhereStatement(dataTransSource.getOnStatement());
                whereStatement.addNextWhereStatement(nextWhereStatement, WhereStatement.LogicType.AND);
                whereStatement = nextWhereStatement;
            }
        }
        return topStatement;
    }

    default List<JoinStatement> createJoinStatements(List<DataTransSource> sortedDataTransSources) {
        List<JoinStatement> result = sortedDataTransSources.stream().filter(x -> !StringUtils.isEmpty(x.getJoinType()))
                .map(x -> JoinStatement.builder()
                        .selectSQLBuilder(new StringStatement(x.getSourceTable()))
                        .joinTableAlias(StringUtils.isEmpty(x.getAlias()) ? x.getSourceTable() : x.getAlias())
                        .joinType(x.getJoinType())
                        .onStatement(x.getOnStatement()).build()).collect(Collectors.toList());
        return result;
    }

    default List<JoinStatement> createCommaJoinStatements(List<DataTransSource> sortedDataTransSources) {
        List<JoinStatement> result = sortedDataTransSources.stream().map(x -> JoinStatement.builder()
                        .selectSQLBuilder(new StringStatement(x.getSourceTable()))
                        .joinTableAlias(StringUtils.isEmpty(x.getAlias()) ? x.getSourceTable() : x.getAlias())
                        .joinType(",").build()).collect(Collectors.toList());
        return result;
    }

    default ISQLBuilder createSelectSQLBuilder(List<DataTransSource> dataTransSources
            , List<DataTransColumn> dataTransColumns, boolean withColumnAlias
            , String prefix, String suffix) {
        List<DataTransSource> sortedSourceList = dataTransSources.stream().sorted(Comparator.comparing(DataTransSource::getDataTransSourceId)).collect(Collectors.toList());
        SelectSQLBuilder.SelectSQLBuilderBuilder selectSQLBuilderBuilder = SelectSQLBuilder.builder();
        selectSQLBuilderBuilder.mainTable(new StringStatement(sortedSourceList.get(0).getSourceTable()));
        selectSQLBuilderBuilder.selectColumns(dataTransColumns.stream()
                .map(x -> x.getColumnValue() + " " + (withColumnAlias?x.getColumnName():""))
                .collect(Collectors.toList()));
        List<JoinStatement> joinStatementList = createJoinStatements(dataTransSources);

        WhereStatement whereStatement = createWhereStatement(dataTransSources);
        selectSQLBuilderBuilder.mainTableAlias(sortedSourceList.get(0).getAlias())
                .joinStatementList(joinStatementList)
                .whereStatement(whereStatement)
                .groupColumns(dataTransColumns.stream().filter(x -> x.getGroupFlag()== Constant.CONST_YES)
                        .map(x -> x.getColumnValue()).collect(Collectors.toList()))
                .prefix(prefix)
                .suffix(suffix);
        return selectSQLBuilderBuilder.build();
    }

    default BaseDataTransSqlExecutor createDropTableExecutor(DataTrans dataTrans) {
        String sql = DropTableSQLBuilder.builder().tableName(dataTrans.getTableName()).build().getSql();
        BaseDataTransSqlExecutor baseDataTransSqlExecutor = new BaseDataTransSqlExecutor(sql);
        return baseDataTransSqlExecutor;
    }
    default BaseDataTransSqlExecutor createCreateTableExecutor(DataTrans dataTrans, List<DataTransColumn> dataTransColumns) {
        List<TableColumnProperty> tableColumns = dataTransColumns.stream().filter(x -> !StringUtils.isEmpty(x.getColumnName()))
                .map(x -> TableColumnProperty.builder()
                        .columnName(x.getColumnName())
                        .columnTypeWithLength(x.getColumnType())
                        .nullable(x.getPrimaryKey() != Constant.CONST_YES).build()).collect(Collectors.toList());
        String sql = CreateTableSQLBuilder.builder()
                .tableName(dataTrans.getTableName())
                .ColumnInfoList(tableColumns)
                .build().getSql();
        BaseDataTransSqlExecutor baseDataTransSqlExecutor = new BaseDataTransSqlExecutor( sql);
        return baseDataTransSqlExecutor;
    }
    default BaseDataTransSqlExecutor createTruncateTableExecutor(DataTrans dataTrans) {
        String sql = TruncateTableSQLBuilder.builder().tableName(dataTrans.getTableName()).build().getSql();
        BaseDataTransSqlExecutor baseDataTransSqlExecutor = new BaseDataTransSqlExecutor(sql);
        return baseDataTransSqlExecutor;
    }

    default BaseDataTransSqlExecutor createDeleteValueExecutor(DataTrans dataTrans, List<DataTransSource> dataTransSources) {
        WhereStatement whereStatement = createWhereStatement(dataTransSources);
        String sql = DeleteValueSQLBuilder.builder().deleteTable(dataTrans.getTableName())
                .whereStatement(whereStatement)
                .build().getSql();
        BaseDataTransSqlExecutor baseDataTransSqlExecutor = new BaseDataTransSqlExecutor(sql);
        return baseDataTransSqlExecutor;
    }

    default BaseDataTransSqlExecutor createDeleteExistExecutor(DataTrans dataTrans, List<DataTransSource> dataTransSources) {
        ISQLBuilder selectSQLBuilder = createSelectSQLBuilder(dataTransSources
                , Collections.singletonList(DataTransColumn.builder().columnValue("*").build())
                , false, "", "");

        String sql = DeleteExistSQLBuilder.builder().deleteTable(dataTrans.getTableName())
                .selectSqlBuilder(selectSQLBuilder)
                .build().getSql();
        BaseDataTransSqlExecutor baseDataTransSqlExecutor = new BaseDataTransSqlExecutor(sql);
        return baseDataTransSqlExecutor;
    }

    default BaseDataTransSqlExecutor createInsertSelectExecutor(DataTrans dataTrans
            , List<DataTransSource> dataTransSources
            , List<DataTransColumn> dataTransColumns) {
        ISQLBuilder selectSQLBuilder = createSelectSQLBuilder(dataTransSources
                , dataTransColumns, true
                , dataTrans.getSelectPrefix(), dataTrans.getSelectSuffix());

        String sql = InsertSelectSQLBuilder.builder().insertColumnList(dataTransColumns.stream()
                .map(x -> x.getColumnName()).collect(Collectors.toList()))
                .insertTable(dataTrans.getTableName())
                .selectSqlBuilder(selectSQLBuilder)
                .build().getSql();
        BaseDataTransSqlExecutor baseDataTransSqlExecutor = new BaseDataTransSqlExecutor(sql);
        return baseDataTransSqlExecutor;
    }


    default BaseDataTransSelectSqlExecutor createSelectToParamExecutor(DataTrans dataTrans
            , List<DataTransSource> dataTransSources
            , List<DataTransColumn> dataTransColumns) {
        ISQLBuilder selectSQLBuilder = createSelectSQLBuilder(dataTransSources
                , dataTransColumns, true
                , dataTrans.getSelectPrefix(), dataTrans.getSelectSuffix());

        BaseDataTransSelectSqlExecutor executor = new BaseDataTransSelectSqlExecutor(selectSQLBuilder.getSql());
        return executor;
    }

    default BaseDataTransSqlExecutor createInsertValueExecutor(DataTrans dataTrans
            , List<DataTransColumn> dataTransColumns) {
        String sql = InsertValueSQLBuilder.builder().columnNameValueMap(dataTransColumns.stream()
                .collect(Collectors.toMap(DataTransColumn::getColumnName, DataTransColumn::getColumnValue)))
                .insertTable(dataTrans.getTableName())
                .build().getSql();
        BaseDataTransSqlExecutor baseDataTransSqlExecutor = new BaseDataTransSqlExecutor(sql);
        return baseDataTransSqlExecutor;
    }

    default BaseDataTransSqlExecutor createUpdateValueExecutor(DataTrans dataTrans, List<DataTransSource> dataTransSources, List<DataTransColumn> dataTransColumns) {
        Map<String, String> columnNameValueMap = dataTransColumns.stream().collect(Collectors.toMap(DataTransColumn::getColumnName, DataTransColumn::getColumnValue));
        WhereStatement whereStatement = createWhereStatement(dataTransSources);
        String sql = UpdateValueSQLBuilder.builder().updateTable(dataTrans.getTableName())
                .columnNameValueMap(columnNameValueMap)
                .whereStatement(whereStatement)
                .build().getSql();
        BaseDataTransSqlExecutor baseDataTransSqlExecutor = new BaseDataTransSqlExecutor(sql);
        return baseDataTransSqlExecutor;
    }

    default BaseDataTransSqlExecutor createUpdateExistExecutor(DataTrans dataTrans, List<DataTransSource> dataTransSources, List<DataTransColumn> dataTransColumns) {
        List<String> columnNameList = dataTransColumns.stream().map(x -> x.getColumnName()).collect(Collectors.toList());
        ISQLBuilder selectSQLBuilder = createSelectSQLBuilder(dataTransSources, dataTransColumns, true
                , "", "");
        ISQLBuilder existSQLBuilder = createSelectSQLBuilder(dataTransSources
                , Collections.singletonList(DataTransColumn.builder().columnValue("*").build()), false
                , "", "");
        String sql = UpdateExistSQLBuilder.builder().updateTable(dataTrans.getTableName())
                .updateColumnList(columnNameList)
                .selectSQLBuilder(selectSQLBuilder)
                .existSQLBuilder(existSQLBuilder)
                .build().getSql();
        BaseDataTransSqlExecutor baseDataTransSqlExecutor = new BaseDataTransSqlExecutor(sql);
        return baseDataTransSqlExecutor;
    }
    default BaseDataTransSqlExecutor createUpdateJoinExecutor(DataTrans dataTrans, List<DataTransSource> dataTransSources, List<DataTransColumn> dataTransColumns) {
        Map<String, String> columnNameValueMap = dataTransColumns.stream().collect(Collectors.toMap(DataTransColumn::getColumnName, DataTransColumn::getColumnValue));
        List<DataTransSource> sortedSourceList = dataTransSources.stream().sorted(Comparator.comparing(DataTransSource::getDataTransSourceId)).collect(Collectors.toList());
        List<JoinStatement> joinStatementList = createCommaJoinStatements(sortedSourceList);
        WhereStatement whereStatement = createWhereStatement(dataTransSources);
        WhereStatement onStatement = createOnStatement(dataTransSources);
        whereStatement.addNextWhereStatement(onStatement, WhereStatement.LogicType.AND);
        String sql = UpdateJoinSQLBuilder.builder().updateTable(dataTrans.getTableName())
                .whereStatement(whereStatement)
                .columnNameValueMap(columnNameValueMap)
                .joinStatements(joinStatementList)
                .build().getSql();
        BaseDataTransSqlExecutor baseDataTransSqlExecutor = new BaseDataTransSqlExecutor(sql);
        return baseDataTransSqlExecutor;
    }

    default BaseDataTransSqlExecutor createAddPrimaryKey1Executor(DataTrans dataTrans
            , List<DataTransColumn> dataTransColumns) {
        List<String> keyColumns = dataTransColumns.stream().filter(x -> x.getPrimaryKey() == Constant.CONST_YES)
                .map(x -> x.getColumnName()).collect(Collectors.toList());
        if(keyColumns.size() == 0) {
            return null;
        }
        String sql = AddTablePrimaryKey1SQLBuilder.builder().tableName(dataTrans.getTableName())
                .primaryKeyName("PK_" + dataTrans.getTableName())
                .columnInfoList(keyColumns)
                .build().getSql();
        BaseDataTransSqlExecutor baseDataTransSqlExecutor = new BaseDataTransSqlExecutor(sql);
        return baseDataTransSqlExecutor;
    }

    default BaseDataTransSqlExecutor createAddPrimaryKey2Executor(DataTrans dataTrans
            , List<DataTransColumn> dataTransColumns) {
        List<String> keyColumns = dataTransColumns.stream().filter(x -> x.getPrimaryKey() == Constant.CONST_YES)
                .map(x -> x.getColumnName()).collect(Collectors.toList());
        if(keyColumns.size() == 0) {
            return null;
        }
        String sql = AddTablePrimaryKey2SQLBuilder.builder().tableName(dataTrans.getTableName())
                .primaryKeyName("PK_" + dataTrans.getTableName())
                .columnInfoList(keyColumns)
                .build().getSql();
        BaseDataTransSqlExecutor baseDataTransSqlExecutor = new BaseDataTransSqlExecutor(sql);
        return baseDataTransSqlExecutor;
    }

    default  BaseListExecutor<Map<String, Object>> createAddIndexExecutor(DataTrans dataTrans
            , List<DataTransColumn> dataTransColumns) {
        Map<String, List<DataTransColumn>> indexMap = dataTransColumns.stream()
                .filter(x -> !StringUtils.isEmpty(x.getIndexName())).collect(
                        Collectors.groupingBy(DataTransColumn::getIndexName)
                );
        if(indexMap.isEmpty()) {
            return null;
        }

        List<BaseDataTransSqlExecutor> sqlExecutors = indexMap.entrySet().stream()
                .map(x -> AddTableIndexSQLBuilder.builder()
                        .tableName(dataTrans.getTableName())
                        .indexName(x.getKey())
                        .isUnique(x.getKey().toLowerCase().startsWith(UNIQUE_INDEX_PREFIX))
                        .columnInfoList(x.getValue().stream()
                        .sorted(Comparator.comparingInt(a -> Optional.ofNullable(a.getIndexOrder()).orElse(0)))
                        .map(y -> y.getColumnName()).collect(Collectors.toList())).build().getSql()
        ).map(x -> new BaseDataTransSqlExecutor(x)).collect(Collectors.toList());

        return new BaseListExecutor(sqlExecutors);
    }
}
