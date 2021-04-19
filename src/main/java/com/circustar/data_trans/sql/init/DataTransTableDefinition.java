package com.circustar.data_trans.sql.init;

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
    public final static String TABLE_NAME_EXEC_PARAM = "DATA_TRANS_EXEC_PARAM";

    public final static String COLUMN_NAME_DATA_TRANS_GROUP_NAME = "DATA_TRANS_GROUP_NAME";
    public final static String COLUMN_NAME_DATA_TRANS_ID = "DATA_TRANS_ID";
    public final static String COLUMN_NAME_DATA_TRANS_EXEC_ID = "DATA_TRANS_EXEC_ID";

    public final static String GROUP_NAME = "_INIT_DATA_TRANS";
    public final static String DATA_TRANS_ID_GROUP = "_INIT_DATA_TRANS_0";
    public final static String DATA_TRANS_ID_TRANS = "_INIT_DATA_TRANS_1";
    public final static String DATA_TRANS_ID_COLUMN = "_INIT_DATA_TRANS_2";
    public final static String DATA_TRANS_ID_SOURCE = "_INIT_DATA_TRANS_3";
    public final static String DATA_TRANS_ID_EXEC = "_INIT_DATA_TRANS_4";
    public final static String DATA_TRANS_ID_EXEC_PARAM = "_INIT_DATA_TRANS_5";

    public final static String DB_NAME_MYSQL = "mysql";
    public final static String DB_NAME_ORACLE = "oracle";
    public final static String DB_NAME_SQLSERVER = "sqlserver";
    public final static String[] SUPPORT_DB = {DB_NAME_MYSQL, DB_NAME_ORACLE, DB_NAME_SQLSERVER};

    private static String getDataType(String dbName, String type, String precision) {
        if(DB_NAME_ORACLE.equalsIgnoreCase(dbName)) {
            if (type.equalsIgnoreCase("datetime")) {
                return "date";
            } else if (type.equalsIgnoreCase("int")) {
                return "number(" + precision + ", 0)" ;
            }
        }
        if(DB_NAME_MYSQL.equalsIgnoreCase(dbName)) {
            if (type.equalsIgnoreCase("datetime")) {
                return "datetime";
            } else if (type.equalsIgnoreCase("int")) {
                return "int(" + precision + ")" ;
            }
        }

        if(DB_NAME_SQLSERVER.equalsIgnoreCase(dbName)) {
            if (type.equalsIgnoreCase("datetime")) {
                return "datetime";
            } else if (type.equalsIgnoreCase("int")) {
                return "int" ;
            }
        }
        return type + (StringUtils.isEmpty(precision)?"":("(" + precision + ")"));
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
                .columnType(getDataType(dbName,"varchar", "100"))
                .primaryKey(1).build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_GROUP)
                .columnName("REMARK")
                .columnType(getDataType(dbName,"varchar", "100"))
                .build());
        return result;
    }

    public static List<DataTransColumn> initColumnDataTrans(String dbName) {
        List<DataTransColumn> result = new ArrayList<>();
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_TRANS)
                .columnName(COLUMN_NAME_DATA_TRANS_ID)
                .columnType(getDataType(dbName,"varchar", "100"))
                .primaryKey(1).build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_TRANS)
                .columnName(COLUMN_NAME_DATA_TRANS_GROUP_NAME)
                .columnType(getDataType(dbName,"varchar", "100"))
                .indexName("IDX_" + TABLE_NAME_TRANS)
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_TRANS)
                .columnName("TABLE_NAME")
                .columnType(getDataType(dbName,"varchar", "100"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_TRANS)
                .columnName("DISABLED")
                .columnType(getDataType(dbName,"int", "10"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_TRANS)
                .columnName("DROP_TABLE_FLAG")
                .columnType(getDataType(dbName,"int", "10"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_TRANS)
                .columnName("CREATE_TABLE_FLAG")
                .columnType(getDataType(dbName,"int", "10"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_TRANS)
                .columnName("TRUNCATE_TABLE_FLAG")
                .columnType(getDataType(dbName,"int", "10"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_TRANS)
                .columnName("UPDATE_TYPE")
                .columnType(getDataType(dbName,"varchar", "10"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_TRANS)
                .columnName("REMARK")
                .columnType(getDataType(dbName,"varchar", "100"))
                .build());
        return result;
    }

    public static List<DataTransColumn> initColumnDataTransSource(String dbName) {
        List<DataTransColumn> result = new ArrayList<>();
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_SOURCE)
                .columnName("DATA_TRANS_SOURCE_ID")
                .columnType(getDataType(dbName,"varchar", "100"))
                .primaryKey(1).build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_SOURCE)
                .columnName(COLUMN_NAME_DATA_TRANS_ID)
                .columnType(getDataType(dbName,"varchar", "100"))
                .indexName("IDX_" + TABLE_NAME_TRANS_SOURCE)
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_SOURCE)
                .columnName("SOURCE_TABLE")
                .columnType(getDataType(dbName,"varchar", "100"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_SOURCE)
                .columnName("ALIAS")
                .columnType(getDataType(dbName,"varchar", "100"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_SOURCE)
                .columnName("JOIN_TYPE")
                .columnType(getDataType(dbName,"varchar", "20"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_SOURCE)
                .columnName("ON_STATEMENT")
                .columnType(getDataType(dbName,"varchar", "1000"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_SOURCE)
                .columnName("WHERE_STATEMENT")
                .columnType(getDataType(dbName,"varchar", "1000"))
                .build());
        return result;
    }

    public static List<DataTransColumn> initColumnDataTransColumn(String dbName) {
        List<DataTransColumn> result = new ArrayList<>();
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_COLUMN)
                .columnName("DATA_TRANS_COLUMN_ID")
                .columnType(getDataType(dbName,"varchar", "100"))
                .primaryKey(1).build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_COLUMN)
                .columnName(COLUMN_NAME_DATA_TRANS_ID)
                .columnType(getDataType(dbName,"varchar", "100"))
                .indexName("IDX_" + TABLE_NAME_TRANS_COLUMN)
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_COLUMN)
                .columnName("COLUMN_NAME")
                .columnType(getDataType(dbName,"varchar", "100"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_COLUMN)
                .columnName("COLUMN_TYPE")
                .columnType(getDataType(dbName,"varchar", "100"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_COLUMN)
                .columnName("COLUMN_VALUE")
                .columnType(getDataType(dbName,"varchar", "2000"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_COLUMN)
                .columnName("GROUP_FLAG")
                .columnType(getDataType(dbName,"int", "10"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_COLUMN)
                .columnName("PRIMARY_KEY")
                .columnType("int(10)")
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_COLUMN)
                .columnName("INDEX_NAME")
                .columnType(getDataType(dbName,"varchar", "100"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_COLUMN)
                .columnName("INDEX_ORDER")
                .columnType(getDataType(dbName,"int", "10"))
                .build());

        return result;
    }

    public static List<DataTransColumn> initColumnDataTransExec(String dbName) {
        List<DataTransColumn> result = new ArrayList<>();
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC)
                .columnName(COLUMN_NAME_DATA_TRANS_EXEC_ID)
                .columnType(getDataType(dbName,"int", "10"))
                .primaryKey(1).build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC)
                .columnName(COLUMN_NAME_DATA_TRANS_GROUP_NAME)
                .columnType(getDataType(dbName,"varchar", "100"))
                .indexName("IDX_" + TABLE_NAME_EXEC)
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC)
                .columnName("EXECUTED")
                .columnType(getDataType(dbName,"int", "10"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC)
                .columnName("EXECUTE_ERROR")
                .columnType(getDataType(dbName,"int", "10"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC)
                .columnName("EXECUTE_TIME")
                .columnType(getDataType(dbName,"datetime", ""))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC)
                .columnName("MESSAGE")
                .columnType(getDataType(dbName,"varchar", "1000"))
                .build());
        return result;
    }
    public static List<DataTransColumn> initColumnDataTransExecParam(String dbName) {
        List<DataTransColumn> result = new ArrayList<>();
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC_PARAM)
                .columnName("DATA_TRANS_EXEC_PARAM_ID")
                .columnType(getDataType(dbName, "int", "10"))
                .primaryKey(1).build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC_PARAM)
                .columnName(COLUMN_NAME_DATA_TRANS_EXEC_ID)
                .columnType(getDataType(dbName, "int", "10"))
                .indexName("IDX_" + TABLE_NAME_EXEC_PARAM)
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC_PARAM)
                .columnName("PARAM_NAME")
                .columnType(getDataType(dbName,"varchar", "100"))
                .build());
        result.add(DataTransColumn.builder()
                .dataTransId(DATA_TRANS_ID_EXEC_PARAM)
                .columnName("PARAM_VALUE")
                .columnType(getDataType(dbName,"varchar", "1000"))
                .build());
        return result;
    }
}
