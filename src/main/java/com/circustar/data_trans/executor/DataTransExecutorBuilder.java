package com.circustar.data_trans.executor;

import com.circustar.data_trans.common.Constant;
import com.circustar.data_trans.common.UpdateType;
import com.circustar.data_trans.entity.DataTrans;
import com.circustar.data_trans.entity.DataTransColumn;
import com.circustar.data_trans.entity.DataTransSource;
import com.circustar.common_utils.executor.*;
import com.circustar.data_trans.executor.init.DataTransTableDefinition;
import org.springframework.util.StringUtils;

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
            listExecutor.addExecutor(new BaseParallelExecutor(
                    createDropTableExecutor(dataTrans)
                            , new EmptyConsumerExecutor<>())
            );
        }
        //生成Create
        if(dataTrans.getCreateTableFlag() == Constant.CONST_YES) {
            listExecutor.addExecutor(new BaseParallelExecutor(
                    createCreateTableExecutor(dataTrans, dataTransColumns)
                    , new EmptyConsumerExecutor<>()));
        }
        //生成Truncate
        if(dataTrans.getTruncateTableFlag() == Constant.CONST_YES) {
            listExecutor.addExecutor(createTruncateTableExecutor(dataTrans));
        }

        UpdateType updateType = UpdateType.parseUpdateType(dataTrans.getUpdateType());
        if(UpdateType.DELETE.equals(updateType)) {
            //生成Delete
            listExecutor.addExecutor(
                    new BaseParallelExecutor(
                            createDeleteValueExecutor(dataTrans, dataTransSources)
                            , createDeleteExistExecutor(dataTrans, dataTransSources)
            ).setPopElementOnSuccess(true));
        }
        if(UpdateType.INSERT.equals(updateType)) {
            BaseParallelExecutor parallelExecutor = null;
            //Add to map
            //BaseDataTransSqlExecutor insertValueExecutor = createInsertValueExecutor(dataTrans, dataTransColumns);
            if(dataTrans.getAddToParamMap() == Constant.CONST_YES) {
                parallelExecutor = new BaseParallelExecutor(
                        createSelectToParamExecutor(dataTrans, dataTransSources, dataTransColumns));
                listExecutor.addExecutor(parallelExecutor.setPopElementOnSuccess(true));
            }
            //生成Insert
            if(!StringUtils.isEmpty(dataTrans.getTableName())) {
                parallelExecutor = new BaseParallelExecutor(
                        createInsertSelectExecutor(dataTrans, dataTransSources, dataTransColumns));
                listExecutor.addExecutor(parallelExecutor.setPopElementOnSuccess(true));
            }
        }
        if(UpdateType.UPDATE.equals(updateType)) {
            //生成Update
            listExecutor.addExecutor(
                    new BaseParallelExecutor(
                            createUpdateValueExecutor(dataTrans, dataTransSources, dataTransColumns),
                            createUpdateExistExecutor(dataTrans, dataTransSources, dataTransColumns),
                            createUpdateJoinExecutor(dataTrans, dataTransSources, dataTransColumns)
                    ).setPopElementOnSuccess(true));
        }

        List<BaseDataTransSqlExecutor> primaryKeyExecutors = Arrays.asList(createAddPrimaryKey1Executor(dataTrans, dataTransColumns)
                , createAddPrimaryKey2Executor(dataTrans, dataTransColumns)).stream()
                .filter(x -> x != null).collect(Collectors.toList());
        if(primaryKeyExecutors != null && primaryKeyExecutors.size() > 0) {
            listExecutor.addExecutor(
                    new BaseParallelExecutor(
                            primaryKeyExecutors
                    ).setPopElementOnSuccess(true));
        }
        listExecutor.addExecutor(createAddIndexExecutor(dataTrans, dataTransColumns));
        return listExecutor;
    }
}
