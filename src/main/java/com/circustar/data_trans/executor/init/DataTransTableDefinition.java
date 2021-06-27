package com.circustar.data_trans.executor.init;

import com.circustar.data_trans.entity.DataTrans;
import com.circustar.data_trans.entity.DataTransColumn;
import com.circustar.data_trans.entity.DataTransGroup;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class DataTransTableDefinition {
    public final static String TABLE_NAME_TRANS_GROUP = "DATA_TRANS_GROUP";
    public final static String TABLE_NAME_TRANS = "DATA_TRANS";
    public final static String TABLE_NAME_TRANS_COLUMN = "DATA_TRANS_COLUMN";
    public final static String TABLE_NAME_TRANS_SOURCE = "DATA_TRANS_SOURCE";
    public final static String TABLE_NAME_EXEC = "DATA_TRANS_EXEC";
    public final static String TABLE_NAME_EXEC_STEP = "DATA_TRANS_EXEC_STEP";
    public final static String TABLE_NAME_EXEC_PARAM = "DATA_TRANS_EXEC_PARAM";

    public final static String COLUMN_NAME_DATA_TRANS_GROUP_NAME = "DATA_TRANS_GROUP_NAME";
    public final static String COLUMN_NAME_DATA_TRANS_ID = "DATA_TRANS_ID";
    public final static String COLUMN_NAME_DATA_TRANS_SOURCE_ID = "DATA_TRANS_SOURCE_ID";
    public final static String COLUMN_NAME_DATA_TRANS_COLUMN_ID = "DATA_TRANS_COLUMN_ID";
    public final static String COLUMN_NAME_DATA_TRANS_EXEC_ID = "DATA_TRANS_EXEC_ID";
    public final static String COLUMN_NAME_DATA_TRANS_EXEC_PARAM_ID = "DATA_TRANS_EXEC_PARAM_ID";
    public final static String COLUMN_NAME_DATA_TRANS_EXEC_STEP_ID = "DATA_TRANS_EXEC_STEP_ID";

    public final static String GROUP_NAME = "_INIT_DATA_TRANS";
    public final static String DATA_TRANS_ID_GROUP = "_INIT_DATA_TRANS_0";
    public final static String DATA_TRANS_ID_TRANS = "_INIT_DATA_TRANS_1";
    public final static String DATA_TRANS_ID_COLUMN = "_INIT_DATA_TRANS_2";
    public final static String DATA_TRANS_ID_SOURCE = "_INIT_DATA_TRANS_3";
    public final static String DATA_TRANS_ID_EXEC = "_INIT_DATA_TRANS_4";
    public final static String DATA_TRANS_ID_EXEC_STEP = "_INIT_DATA_TRANS_5";
    public final static String DATA_TRANS_ID_EXEC_PARAM = "_INIT_DATA_TRANS_6";

    public final static String COLUMN_TYPE_VARCHAR = "varchar";
    public final static String COLUMN_TYPE_INT = "int";
    public final static String COLUMN_TYPE_NUMBER = "number";
    public final static String COLUMN_TYPE_DATE = "date";
    public final static String COLUMN_TYPE_DATETIME = "datetime";

    public final static String DB_NAME_MYSQL = "mysql";
    public final static String DB_NAME_ORACLE = "oracle";
    public final static String DB_NAME_SQLSERVER = "sqlserver";
    public final static String DB_NAME_DB2 = "db2";
    public final static String[] SUPPORT_DB = {DB_NAME_MYSQL, DB_NAME_ORACLE, DB_NAME_SQLSERVER};

    private static String getDataType(String dbName, String type, String precision) {
        if(DB_NAME_ORACLE.equalsIgnoreCase(dbName)) {
            if (type.equalsIgnoreCase(COLUMN_TYPE_DATETIME)) {
                return COLUMN_TYPE_DATE;
            } else if (type.equalsIgnoreCase(COLUMN_TYPE_INT)) {
                return COLUMN_TYPE_NUMBER + "(" + precision + ", 0)" ;
            }
        }
        if(DB_NAME_MYSQL.equalsIgnoreCase(dbName)) {
            if (type.equalsIgnoreCase(COLUMN_TYPE_DATETIME)) {
                return COLUMN_TYPE_DATETIME;
            } else if (type.equalsIgnoreCase(COLUMN_TYPE_INT)) {
                return COLUMN_TYPE_INT +  "(" + precision + ")" ;
            }
        }

        if(DB_NAME_SQLSERVER.equalsIgnoreCase(dbName)) {
            if (type.equalsIgnoreCase(COLUMN_TYPE_DATETIME)) {
                return COLUMN_TYPE_DATETIME;
            } else if (type.equalsIgnoreCase(COLUMN_TYPE_INT)) {
                return COLUMN_TYPE_INT ;
            }
        }
        return type + (!StringUtils.hasLength(precision)?"":("(" + precision + ")"));
    }

    public static DataTransGroup initGroup() {
        return DataTransGroup.builder().dataTransGroupName(GROUP_NAME).build();
    }

    public static DataTrans initDataTrans(DataTransGroup group, String transId, String tableName) {
        return DataTrans.builder().dataTransGroupName(group.getDataTransGroupName())
                .dataTransId(transId)
                .tableName(tableName)
                .createTableFlag(1)
                .build();
    }

    public static List<DataTransColumn> initColumnDataTransGroup(String dbName) {
        List<DataTransColumn> result = new ArrayList<>();
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_GROUP)
                .columnName(COLUMN_NAME_DATA_TRANS_GROUP_NAME)
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "30"))
                .primaryKey(1).build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_GROUP)
                .columnName("RECOVERABLE")
                .columnType(getDataType(dbName,COLUMN_TYPE_INT, "10"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_GROUP)
                .columnName("REMARK")
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "200"))
                .build());
        return result;
    }

    public static List<DataTransColumn> initColumnDataTrans(String dbName) {
        List<DataTransColumn> result = new ArrayList<>();
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_TRANS)
                .columnName(COLUMN_NAME_DATA_TRANS_ID)
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "100"))
                .primaryKey(1).build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_TRANS)
                .columnName(COLUMN_NAME_DATA_TRANS_GROUP_NAME)
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "100"))
                .indexName("IDX_" + TABLE_NAME_TRANS)
                .indexOrder(1)
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_TRANS)
                .columnName("DEPEND_DATA_TRANS_ID")
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "100"))
                .indexName("IDX_" + TABLE_NAME_TRANS)
                .indexOrder(2)
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_TRANS)
                .columnName("TABLE_NAME")
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "100"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_TRANS)
                .columnName("DISABLED")
                .columnType(getDataType(dbName,COLUMN_TYPE_INT, "10"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_TRANS)
                .columnName("DROP_TABLE_FLAG")
                .columnType(getDataType(dbName,COLUMN_TYPE_INT, "10"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_TRANS)
                .columnName("CREATE_TABLE_FLAG")
                .columnType(getDataType(dbName,COLUMN_TYPE_INT, "10"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_TRANS)
                .columnName("TRUNCATE_TABLE_FLAG")
                .columnType(getDataType(dbName,COLUMN_TYPE_INT, "10"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_TRANS)
                .columnName("UPDATE_TYPE")
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "10"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_TRANS)
                .columnName("SELECT_PREFIX")
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "50"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_TRANS)
                .columnName("SELECT_SUFFIX")
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "50"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_TRANS)
                .columnName("SKIP_EXPRESSION")
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "200"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_TRANS)
                .columnName("ADD_TO_PARAM_MAP")
                .columnType(getDataType(dbName,COLUMN_TYPE_INT, "10"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_TRANS)
                .columnName("REMARK")
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "200"))
                .build());
        return result;
    }

    public static List<DataTransColumn> initColumnDataTransSource(String dbName) {
        List<DataTransColumn> result = new ArrayList<>();
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_SOURCE)
                .columnName(COLUMN_NAME_DATA_TRANS_SOURCE_ID)
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "100"))
                .primaryKey(1).build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_SOURCE)
                .columnName(COLUMN_NAME_DATA_TRANS_ID)
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "100"))
                .indexName("IDX_" + TABLE_NAME_TRANS_SOURCE)
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_SOURCE)
                .columnName("SOURCE_TABLE")
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "100"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_SOURCE)
                .columnName("ALIAS")
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "30"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_SOURCE)
                .columnName("JOIN_TYPE")
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "20"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_SOURCE)
                .columnName("ON_STATEMENT")
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "1000"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_SOURCE)
                .columnName("WHERE_STATEMENT")
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "2000"))
                .build());
        return result;
    }

    public static List<DataTransColumn> initColumnDataTransColumn(String dbName) {
        List<DataTransColumn> result = new ArrayList<>();
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_COLUMN)
                .columnName(COLUMN_NAME_DATA_TRANS_COLUMN_ID)
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "100"))
                .primaryKey(1).build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_COLUMN)
                .columnName(COLUMN_NAME_DATA_TRANS_ID)
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "100"))
                .indexName("IDX_" + TABLE_NAME_TRANS_COLUMN)
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_COLUMN)
                .columnName("COLUMN_NAME")
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "100"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_COLUMN)
                .columnName("COLUMN_TYPE")
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "30"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_COLUMN)
                .columnName("COLUMN_VALUE")
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "2000"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_COLUMN)
                .columnName("GROUP_FLAG")
                .columnType(getDataType(dbName,COLUMN_TYPE_INT, "10"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_COLUMN)
                .columnName("PRIMARY_KEY")
                .columnType("int(10)")
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_COLUMN)
                .columnName("INDEX_NAME")
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "100"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_COLUMN)
                .columnName("INDEX_ORDER")
                .columnType(getDataType(dbName,COLUMN_TYPE_INT, "10"))
                .build());

        return result;
    }

    public static List<DataTransColumn> initColumnDataTransExec(String dbName) {
        List<DataTransColumn> result = new ArrayList<>();
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC)
                .columnName(COLUMN_NAME_DATA_TRANS_EXEC_ID)
                .columnType(getDataType(dbName,COLUMN_TYPE_INT, "10"))
                .primaryKey(1).build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC)
                .columnName(COLUMN_NAME_DATA_TRANS_GROUP_NAME)
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "100"))
                .indexName("IDX_" + TABLE_NAME_EXEC)
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC)
                .columnName("EXECUTED")
                .columnType(getDataType(dbName,COLUMN_TYPE_INT, "10"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC)
                .columnName("EXECUTE_ERROR")
                .columnType(getDataType(dbName,COLUMN_TYPE_INT, "10"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC)
                .columnName("EXECUTE_TIME")
                .columnType(getDataType(dbName,COLUMN_TYPE_DATETIME, ""))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC)
                .columnName("MESSAGE")
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "1500"))
                .build());
        return result;
    }

    public static List<DataTransColumn> initColumnDataTransExecStep(String dbName) {
        List<DataTransColumn> result = new ArrayList<>();
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC_STEP)
                .columnName(COLUMN_NAME_DATA_TRANS_EXEC_STEP_ID)
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "32"))
                .primaryKey(1).build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC_STEP)
                .columnName(COLUMN_NAME_DATA_TRANS_EXEC_ID)
                .columnType(getDataType(dbName,COLUMN_TYPE_INT, "10"))
                .indexName("UNI_IDX_" + TABLE_NAME_EXEC_STEP)
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC_STEP)
                .columnName(COLUMN_NAME_DATA_TRANS_ID)
                .columnType(getDataType(dbName,COLUMN_TYPE_INT, "10"))
                .indexName("UNI_IDX_" + TABLE_NAME_EXEC_STEP)
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC_STEP)
                .columnName("EXECUTED")
                .columnType(getDataType(dbName,COLUMN_TYPE_INT, "10"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC_STEP)
                .columnName("EXECUTE_ERROR")
                .columnType(getDataType(dbName,COLUMN_TYPE_INT, "10"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC_STEP)
                .columnName("EXECUTE_TIME")
                .columnType(getDataType(dbName,COLUMN_TYPE_DATETIME, ""))
                .build());
        return result;
    }

    public static List<DataTransColumn> initColumnDataTransExecParam(String dbName) {
        List<DataTransColumn> result = new ArrayList<>();
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC_PARAM)
                .columnName(COLUMN_NAME_DATA_TRANS_EXEC_PARAM_ID)
                .columnType(getDataType(dbName, COLUMN_TYPE_INT, "10"))
                .primaryKey(1).build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC_PARAM)
                .columnName(COLUMN_NAME_DATA_TRANS_EXEC_ID)
                .columnType(getDataType(dbName, COLUMN_TYPE_INT, "10"))
                .indexName("IDX_" + TABLE_NAME_EXEC_PARAM)
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC_PARAM)
                .columnName("PARAM_NAME")
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "50"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC_PARAM)
                .columnName("PARAM_VALUE")
                .columnType(getDataType(dbName,COLUMN_TYPE_VARCHAR, "1000"))
                .build());
        return result;
    }
}
