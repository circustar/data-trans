package com.circustar.data_trans.sql.init;

import com.circustar.data_trans.entity.DataTrans;
import com.circustar.data_trans.entity.DataTransGroup;
import com.circustar.data_trans.sql.executor.IDataTransExecutorBuilder;
import com.circustar.util.executor.*;

import java.util.*;

public class DataTransInitExecutorBuilder implements IDataTransExecutorBuilder {
    public IExecutor<Map<String, Object>> build() {
        IListExecutor<Map<String, Object>> listExecutor = new BaseListExecutor<>();
        //生成DATA_TRANS_GROUP
        DataTransGroup dataTransGroup = DataTransTableDefinition.initGroup();
        DataTrans dataTrans0 = DataTransTableDefinition.initDataTrans(dataTransGroup, DataTransTableDefinition.DATA_TRANS_ID_GROUP, DataTransTableDefinition.TABLE_NAME_TRANS_GROUP);
        listExecutor.addExecutor(new BaseSuccessExitListExecutor(
                createCreateTableExecutor(dataTrans0
                        , DataTransTableDefinition.initColumnDataTransGroup(DataTransTableDefinition.DB_NAME_MYSQL))
                , createCreateTableExecutor(dataTrans0
                , DataTransTableDefinition.initColumnDataTransGroup(DataTransTableDefinition.DB_NAME_ORACLE))
                , createCreateTableExecutor(dataTrans0
                , DataTransTableDefinition.initColumnDataTransGroup(DataTransTableDefinition.DB_NAME_SQLSERVER))
                , new EmptyExecutor<>()));

        listExecutor.addExecutor(new BaseSuccessExitListExecutor(
                createAddPrimaryKey1Executor(dataTrans0
                        , DataTransTableDefinition.initColumnDataTransGroup(""))
                , createAddPrimaryKey2Executor(dataTrans0
                , DataTransTableDefinition.initColumnDataTransGroup(""))
                , new EmptyExecutor<>()));

        listExecutor.addExecutor(new BaseSuccessExitListExecutor(
                createAddIndexExecutor(dataTrans0
                        , DataTransTableDefinition.initColumnDataTransGroup(""))
                , new EmptyExecutor<>()));

        DataTrans dataTrans1 = DataTransTableDefinition.initDataTrans(dataTransGroup, DataTransTableDefinition.DATA_TRANS_ID_TRANS, DataTransTableDefinition.TABLE_NAME_TRANS);
        listExecutor.addExecutor(new BaseSuccessExitListExecutor(
                createCreateTableExecutor(dataTrans1
                        , DataTransTableDefinition.initColumnDataTrans(DataTransTableDefinition.DB_NAME_MYSQL))
                , createCreateTableExecutor(dataTrans1
                , DataTransTableDefinition.initColumnDataTrans(DataTransTableDefinition.DB_NAME_ORACLE))
                , createCreateTableExecutor(dataTrans1
                , DataTransTableDefinition.initColumnDataTrans(DataTransTableDefinition.DB_NAME_SQLSERVER))
                , new EmptyExecutor<>()));

        listExecutor.addExecutor(new BaseSuccessExitListExecutor(
                createAddPrimaryKey1Executor(dataTrans1
                        , DataTransTableDefinition.initColumnDataTrans(""))
                , createAddPrimaryKey2Executor(dataTrans1
                , DataTransTableDefinition.initColumnDataTrans(""))
                , new EmptyExecutor<>()));

        listExecutor.addExecutor(new BaseSuccessExitListExecutor(
                createAddIndexExecutor(dataTrans1
                        , DataTransTableDefinition.initColumnDataTrans(""))
                , new EmptyExecutor<>()));

        DataTrans dataTrans2 = DataTransTableDefinition.initDataTrans(dataTransGroup, DataTransTableDefinition.DATA_TRANS_ID_SOURCE, DataTransTableDefinition.TABLE_NAME_TRANS_SOURCE);
        listExecutor.addExecutor(new BaseSuccessExitListExecutor(
                createCreateTableExecutor(dataTrans2
                        , DataTransTableDefinition.initColumnDataTransSource(DataTransTableDefinition.DB_NAME_MYSQL))
                , createCreateTableExecutor(dataTrans2
                , DataTransTableDefinition.initColumnDataTransSource(DataTransTableDefinition.DB_NAME_ORACLE))
                , createCreateTableExecutor(dataTrans2
                , DataTransTableDefinition.initColumnDataTransSource(DataTransTableDefinition.DB_NAME_SQLSERVER))
                , new EmptyExecutor<>()));

        listExecutor.addExecutor(new BaseSuccessExitListExecutor(
                createAddPrimaryKey1Executor(dataTrans2
                        , DataTransTableDefinition.initColumnDataTransSource(""))
                , createAddPrimaryKey2Executor(dataTrans2
                , DataTransTableDefinition.initColumnDataTransSource(""))
                , new EmptyExecutor<>()));

        listExecutor.addExecutor(new BaseSuccessExitListExecutor(
                createAddIndexExecutor(dataTrans2
                        , DataTransTableDefinition.initColumnDataTransSource(""))
                , new EmptyExecutor<>()));

        DataTrans dataTrans3 = DataTransTableDefinition.initDataTrans(dataTransGroup, DataTransTableDefinition.DATA_TRANS_ID_COLUMN, DataTransTableDefinition.TABLE_NAME_TRANS_COLUMN);
        listExecutor.addExecutor(new BaseSuccessExitListExecutor(
                createCreateTableExecutor(dataTrans3
                        , DataTransTableDefinition.initColumnDataTransColumn(DataTransTableDefinition.DB_NAME_MYSQL))
                , createCreateTableExecutor(dataTrans3
                , DataTransTableDefinition.initColumnDataTransColumn(DataTransTableDefinition.DB_NAME_ORACLE))
                , createCreateTableExecutor(dataTrans3
                , DataTransTableDefinition.initColumnDataTransColumn(DataTransTableDefinition.DB_NAME_SQLSERVER))
                , new EmptyExecutor<>()));

        listExecutor.addExecutor(new BaseSuccessExitListExecutor(
                createAddPrimaryKey1Executor(dataTrans3
                        , DataTransTableDefinition.initColumnDataTransColumn(""))
                , createAddPrimaryKey2Executor(dataTrans3
                , DataTransTableDefinition.initColumnDataTransColumn(""))
                , new EmptyExecutor<>()));

        listExecutor.addExecutor(new BaseSuccessExitListExecutor(
                createAddIndexExecutor(dataTrans3
                        , DataTransTableDefinition.initColumnDataTransColumn(""))
                , new EmptyExecutor<>()));

        DataTrans dataTrans4 = DataTransTableDefinition.initDataTrans(dataTransGroup, DataTransTableDefinition.DATA_TRANS_ID_EXEC, DataTransTableDefinition.TABLE_NAME_EXEC);
        listExecutor.addExecutor(new BaseSuccessExitListExecutor(
                createCreateTableExecutor(dataTrans4
                        , DataTransTableDefinition.initColumnDataTransExec(DataTransTableDefinition.DB_NAME_MYSQL))
                , createCreateTableExecutor(dataTrans4
                , DataTransTableDefinition.initColumnDataTransExec(DataTransTableDefinition.DB_NAME_ORACLE))
                , createCreateTableExecutor(dataTrans4
                , DataTransTableDefinition.initColumnDataTransExec(DataTransTableDefinition.DB_NAME_SQLSERVER))
                , new EmptyExecutor<>()));

        listExecutor.addExecutor(new BaseSuccessExitListExecutor(
                createAddPrimaryKey1Executor(dataTrans4
                        , DataTransTableDefinition.initColumnDataTransExec(""))
                , createAddPrimaryKey2Executor(dataTrans4
                , DataTransTableDefinition.initColumnDataTransExec(""))
                , new EmptyExecutor<>()));

        listExecutor.addExecutor(new BaseSuccessExitListExecutor(
                createAddIndexExecutor(dataTrans4
                        , DataTransTableDefinition.initColumnDataTransExec(""))
                , new EmptyExecutor<>()));

        DataTrans dataTrans5 = DataTransTableDefinition.initDataTrans(dataTransGroup, DataTransTableDefinition.DATA_TRANS_ID_EXEC_PARAM, DataTransTableDefinition.TABLE_NAME_EXEC_PARAM);
        listExecutor.addExecutor(new BaseSuccessExitListExecutor(
                createCreateTableExecutor(dataTrans5
                        , DataTransTableDefinition.initColumnDataTransExecParam(DataTransTableDefinition.DB_NAME_MYSQL))
                , createCreateTableExecutor(dataTrans5
                , DataTransTableDefinition.initColumnDataTransExecParam(DataTransTableDefinition.DB_NAME_ORACLE))
                , createCreateTableExecutor(dataTrans5
                , DataTransTableDefinition.initColumnDataTransExecParam(DataTransTableDefinition.DB_NAME_SQLSERVER))
                , new EmptyExecutor<>()));

        listExecutor.addExecutor(new BaseSuccessExitListExecutor(
                createAddPrimaryKey1Executor(dataTrans5
                        , DataTransTableDefinition.initColumnDataTransExecParam(""))
                , createAddPrimaryKey2Executor(dataTrans5
                , DataTransTableDefinition.initColumnDataTransExecParam(""))
                , new EmptyExecutor<>()));

        listExecutor.addExecutor(new BaseSuccessExitListExecutor(
                createAddIndexExecutor(dataTrans5
                        , DataTransTableDefinition.initColumnDataTransExecParam(""))
                , new EmptyExecutor<>()));

        return listExecutor;
    }

}
