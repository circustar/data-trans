package com.circustar.data_trans.sql.executor;

import com.circustar.data_trans.common.Constant;
import com.circustar.data_trans.common.UpdateType;
import com.circustar.data_trans.entity.DataTrans;
import com.circustar.data_trans.entity.DataTransColumn;
import com.circustar.data_trans.entity.DataTransSource;
import com.circustar.util.executor.*;

import java.util.*;
import java.util.stream.Collectors;

public class DataTransExecutorBuilder implements IDataTransExecutorBuilder {
    private DataTrans dataTrans;
    private List<DataTransSource> dataTransSources;
    private List<DataTransColumn> dataTransColumns;

    public DataTransExecutorBuilder(DataTrans dataTrans, List<DataTransSource> dataTransSources
            , List<DataTransColumn> dataTransColumns) {
        this.dataTrans = dataTrans;
        this.dataTransSources = dataTransSources;
        this.dataTransColumns = dataTransColumns;
    }
    public IExecutor<Map<String, Object>> build() {
        IListExecutor<Map<String, Object>> listExecutor = new BaseListExecutor<>();
        //生成Drop
        if(dataTrans.getDropTableFlag() == Constant.CONST_YES) {
            listExecutor.addExecutor(new BaseSuccessExitListExecutor(
                    createDropTableExecutor(dataTrans)
                            , new EmptyExecutor<>())
            );
        }
        //生成Create
        if(dataTrans.getCreateTableFlag() == Constant.CONST_YES) {
            listExecutor.addExecutor(new BaseSuccessExitListExecutor(
                    createCreateTableExecutor(dataTrans, dataTransColumns)
                    , new EmptyExecutor<>()));
        }
        //生成Truncate
        if(dataTrans.getTruncateTableFlag() == Constant.CONST_YES) {
            listExecutor.addExecutor(createTruncateTableExecutor(dataTrans));
        }

        UpdateType updateType = UpdateType.parseUpdateType(dataTrans.getUpdateType());
        if(UpdateType.DELETE.equals(updateType)) {
            //生成Delete
            listExecutor.addExecutor(
                    new BaseSuccessExitListExecutor(
                            createDeleteValueExecutor(dataTrans, dataTransSources)
                            , createDeleteExistExecutor(dataTrans, dataTransSources)
            ).setPopElementOnSuccess(true));
        }
        if(UpdateType.INSERT.equals(updateType)) {
            //生成Insert
            listExecutor.addExecutor(
                    new BaseSuccessExitListExecutor(
                            createInsertSelectExecutor(dataTrans, dataTransSources, dataTransColumns),
                            createInsertValueExecutor(dataTrans, dataTransColumns)
                    ).setPopElementOnSuccess(true));
        }
        if(UpdateType.UPDATE.equals(updateType)) {
            //生成Update
            listExecutor.addExecutor(
                    new BaseSuccessExitListExecutor(
                            createUpdateValueExecutor(dataTrans, dataTransSources, dataTransColumns),
                            createUpdateExistExecutor(dataTrans, dataTransSources, dataTransColumns),
                            createUpdateJoinExecutor(dataTrans, dataTransSources, dataTransColumns)
                    ).setPopElementOnSuccess(true));
        }

        List<BaseSqlExecutor> primaryKeyExecutors = Arrays.asList(createAddPrimaryKey1Executor(dataTrans, dataTransColumns)
                , createAddPrimaryKey2Executor(dataTrans, dataTransColumns)).stream()
                .filter(x -> x != null).collect(Collectors.toList());
        if(primaryKeyExecutors != null && primaryKeyExecutors.size() > 0) {
            listExecutor.addExecutor(
                    new BaseSuccessExitListExecutor(
                            primaryKeyExecutors
                    ).setPopElementOnSuccess(true));
        }
        listExecutor.addExecutor(createAddIndexExecutor(dataTrans, dataTransColumns));
        return listExecutor;
    }
}
