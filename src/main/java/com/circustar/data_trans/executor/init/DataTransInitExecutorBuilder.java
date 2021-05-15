package com.circustar.data_trans.executor.init;

import com.circustar.data_trans.entity.DataTrans;
import com.circustar.data_trans.entity.DataTransGroup;
import com.circustar.data_trans.executor.IDataTransExecutorBuilder;
import com.circustar.common_utils.executor.*;

import java.util.*;

public class DataTransInitExecutorBuilder implements IDataTransExecutorBuilder {
    public IExecutor<Map<String, Object>> build() {
        IListExecutor<Map<String, Object>> listExecutor = new BaseListExecutor<>();
        //生成DATA_TRANS_GROUP
        DataTransGroup dataTransGroup = DataTransTableDefinition.initGroup();
        DataTrans dataTrans0 = DataTransTableDefinition.initDataTrans(dataTransGroup, DataTransTableDefinition.DATA_TRANS_ID_GROUP, DataTransTableDefinition.TABLE_NAME_TRANS_GROUP);
        listExecutor.addExecutor(new BaseParallelExecutor(
                createCreateTableExecutor(dataTrans0
                        , DataTransTableDefinition.initColumnDataTransGroup(DataTransTableDefinition.DB_NAME_MYSQL))
                , createCreateTableExecutor(dataTrans0
                , DataTransTableDefinition.initColumnDataTransGroup(DataTransTableDefinition.DB_NAME_ORACLE))
                , createCreateTableExecutor(dataTrans0
                , DataTransTableDefinition.initColumnDataTransGroup(DataTransTableDefinition.DB_NAME_SQLSERVER))
                , new EmptyConsumerExecutor<>()));

        listExecutor.addExecutor(new BaseParallelExecutor(
                createAddPrimaryKey1Executor(dataTrans0
                        , DataTransTableDefinition.initColumnDataTransGroup(""))
                , createAddPrimaryKey2Executor(dataTrans0
                , DataTransTableDefinition.initColumnDataTransGroup(""))
                , new EmptyConsumerExecutor<>()));

        listExecutor.addExecutor(new BaseParallelExecutor(
                createAddIndexExecutor(dataTrans0
                        , DataTransTableDefinition.initColumnDataTransGroup(""))
                , new EmptyConsumerExecutor<>()));

        DataTrans dataTrans1 = DataTransTableDefinition.initDataTrans(dataTransGroup, DataTransTableDefinition.DATA_TRANS_ID_TRANS, DataTransTableDefinition.TABLE_NAME_TRANS);
        listExecutor.addExecutor(new BaseParallelExecutor(
                createCreateTableExecutor(dataTrans1
                        , DataTransTableDefinition.initColumnDataTrans(DataTransTableDefinition.DB_NAME_MYSQL))
                , createCreateTableExecutor(dataTrans1
                , DataTransTableDefinition.initColumnDataTrans(DataTransTableDefinition.DB_NAME_ORACLE))
                , createCreateTableExecutor(dataTrans1
                , DataTransTableDefinition.initColumnDataTrans(DataTransTableDefinition.DB_NAME_SQLSERVER))
                , new EmptyConsumerExecutor<>()));

        listExecutor.addExecutor(new BaseParallelExecutor(
                createAddPrimaryKey1Executor(dataTrans1
                        , DataTransTableDefinition.initColumnDataTrans(""))
                , createAddPrimaryKey2Executor(dataTrans1
                , DataTransTableDefinition.initColumnDataTrans(""))
                , new EmptyConsumerExecutor<>()));

        listExecutor.addExecutor(new BaseParallelExecutor(
                createAddIndexExecutor(dataTrans1
                        , DataTransTableDefinition.initColumnDataTrans(""))
                , new EmptyConsumerExecutor<>()));

        DataTrans dataTrans2 = DataTransTableDefinition.initDataTrans(dataTransGroup, DataTransTableDefinition.DATA_TRANS_ID_SOURCE, DataTransTableDefinition.TABLE_NAME_TRANS_SOURCE);
        listExecutor.addExecutor(new BaseParallelExecutor(
                createCreateTableExecutor(dataTrans2
                        , DataTransTableDefinition.initColumnDataTransSource(DataTransTableDefinition.DB_NAME_MYSQL))
                , createCreateTableExecutor(dataTrans2
                , DataTransTableDefinition.initColumnDataTransSource(DataTransTableDefinition.DB_NAME_ORACLE))
                , createCreateTableExecutor(dataTrans2
                , DataTransTableDefinition.initColumnDataTransSource(DataTransTableDefinition.DB_NAME_SQLSERVER))
                , new EmptyConsumerExecutor<>()));

        listExecutor.addExecutor(new BaseParallelExecutor(
                createAddPrimaryKey1Executor(dataTrans2
                        , DataTransTableDefinition.initColumnDataTransSource(""))
                , createAddPrimaryKey2Executor(dataTrans2
                , DataTransTableDefinition.initColumnDataTransSource(""))
                , new EmptyConsumerExecutor<>()));

        listExecutor.addExecutor(new BaseParallelExecutor(
                createAddIndexExecutor(dataTrans2
                        , DataTransTableDefinition.initColumnDataTransSource(""))
                , new EmptyConsumerExecutor<>()));

        DataTrans dataTrans3 = DataTransTableDefinition.initDataTrans(dataTransGroup, DataTransTableDefinition.DATA_TRANS_ID_COLUMN, DataTransTableDefinition.TABLE_NAME_TRANS_COLUMN);
        listExecutor.addExecutor(new BaseParallelExecutor(
                createCreateTableExecutor(dataTrans3
                        , DataTransTableDefinition.initColumnDataTransColumn(DataTransTableDefinition.DB_NAME_MYSQL))
                , createCreateTableExecutor(dataTrans3
                , DataTransTableDefinition.initColumnDataTransColumn(DataTransTableDefinition.DB_NAME_ORACLE))
                , createCreateTableExecutor(dataTrans3
                , DataTransTableDefinition.initColumnDataTransColumn(DataTransTableDefinition.DB_NAME_SQLSERVER))
                , new EmptyConsumerExecutor<>()));

        listExecutor.addExecutor(new BaseParallelExecutor(
                createAddPrimaryKey1Executor(dataTrans3
                        , DataTransTableDefinition.initColumnDataTransColumn(""))
                , createAddPrimaryKey2Executor(dataTrans3
                , DataTransTableDefinition.initColumnDataTransColumn(""))
                , new EmptyConsumerExecutor<>()));

        listExecutor.addExecutor(new BaseParallelExecutor(
                createAddIndexExecutor(dataTrans3
                        , DataTransTableDefinition.initColumnDataTransColumn(""))
                , new EmptyConsumerExecutor<>()));

        DataTrans dataTrans4 = DataTransTableDefinition.initDataTrans(dataTransGroup, DataTransTableDefinition.DATA_TRANS_ID_EXEC, DataTransTableDefinition.TABLE_NAME_EXEC);
        listExecutor.addExecutor(new BaseParallelExecutor(
                createCreateTableExecutor(dataTrans4
                        , DataTransTableDefinition.initColumnDataTransExec(DataTransTableDefinition.DB_NAME_MYSQL))
                , createCreateTableExecutor(dataTrans4
                , DataTransTableDefinition.initColumnDataTransExec(DataTransTableDefinition.DB_NAME_ORACLE))
                , createCreateTableExecutor(dataTrans4
                , DataTransTableDefinition.initColumnDataTransExec(DataTransTableDefinition.DB_NAME_SQLSERVER))
                , new EmptyConsumerExecutor<>()));

        listExecutor.addExecutor(new BaseParallelExecutor(
                createAddPrimaryKey1Executor(dataTrans4
                        , DataTransTableDefinition.initColumnDataTransExec(""))
                , createAddPrimaryKey2Executor(dataTrans4
                , DataTransTableDefinition.initColumnDataTransExec(""))
                , new EmptyConsumerExecutor<>()));

        listExecutor.addExecutor(new BaseParallelExecutor(
                createAddIndexExecutor(dataTrans4
                        , DataTransTableDefinition.initColumnDataTransExec(""))
                , new EmptyConsumerExecutor<>()));

        DataTrans dataTrans5 = DataTransTableDefinition.initDataTrans(dataTransGroup, DataTransTableDefinition.DATA_TRANS_ID_EXEC_PARAM, DataTransTableDefinition.TABLE_NAME_EXEC_PARAM);
        listExecutor.addExecutor(new BaseParallelExecutor(
                createCreateTableExecutor(dataTrans5
                        , DataTransTableDefinition.initColumnDataTransExecParam(DataTransTableDefinition.DB_NAME_MYSQL))
                , createCreateTableExecutor(dataTrans5
                , DataTransTableDefinition.initColumnDataTransExecParam(DataTransTableDefinition.DB_NAME_ORACLE))
                , createCreateTableExecutor(dataTrans5
                , DataTransTableDefinition.initColumnDataTransExecParam(DataTransTableDefinition.DB_NAME_SQLSERVER))
                , new EmptyConsumerExecutor<>()));

        listExecutor.addExecutor(new BaseParallelExecutor(
                createAddPrimaryKey1Executor(dataTrans5
                        , DataTransTableDefinition.initColumnDataTransExecParam(""))
                , createAddPrimaryKey2Executor(dataTrans5
                , DataTransTableDefinition.initColumnDataTransExecParam(""))
                , new EmptyConsumerExecutor<>()));

        listExecutor.addExecutor(new BaseParallelExecutor(
                createAddIndexExecutor(dataTrans5
                        , DataTransTableDefinition.initColumnDataTransExecParam(""))
                , new EmptyConsumerExecutor<>()));

        DataTrans dataTrans6 = DataTransTableDefinition.initDataTrans(dataTransGroup, DataTransTableDefinition.DATA_TRANS_ID_EXEC_STEP, DataTransTableDefinition.TABLE_NAME_EXEC_STEP);
        listExecutor.addExecutor(new BaseParallelExecutor(
                createCreateTableExecutor(dataTrans6
                        , DataTransTableDefinition.initColumnDataTransExecStep(DataTransTableDefinition.DB_NAME_MYSQL))
                , createCreateTableExecutor(dataTrans6
                , DataTransTableDefinition.initColumnDataTransExecStep(DataTransTableDefinition.DB_NAME_ORACLE))
                , createCreateTableExecutor(dataTrans6
                , DataTransTableDefinition.initColumnDataTransExecStep(DataTransTableDefinition.DB_NAME_SQLSERVER))
                , new EmptyConsumerExecutor<>()));

        listExecutor.addExecutor(new BaseParallelExecutor(
                createAddPrimaryKey1Executor(dataTrans6
                        , DataTransTableDefinition.initColumnDataTransExecStep(""))
                , createAddPrimaryKey2Executor(dataTrans6
                , DataTransTableDefinition.initColumnDataTransExecStep(""))
                , new EmptyConsumerExecutor<>()));

        listExecutor.addExecutor(new BaseParallelExecutor(
                createAddIndexExecutor(dataTrans6
                        , DataTransTableDefinition.initColumnDataTransExecStep(""))
                , new EmptyConsumerExecutor<>()));

        return listExecutor;
    }

}
