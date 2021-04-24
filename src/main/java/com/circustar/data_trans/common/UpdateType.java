package com.circustar.data_trans.common;

public enum UpdateType {
    INSERT("insert"),
    UPDATE("update"),
    DELETE("delete");
    private String name;

    UpdateType(String name) {
        this.name = name;
    }

    public static UpdateType parseUpdateType(String name) {
        String strName = name.toLowerCase();
        if(UpdateType.DELETE.name.equalsIgnoreCase(strName)) {
            return UpdateType.DELETE;
        }
        if(UpdateType.INSERT.name.equalsIgnoreCase(strName)) {
            return UpdateType.INSERT;
        }
        if(UpdateType.UPDATE.name.equalsIgnoreCase(strName)) {
            return UpdateType.UPDATE;
        }
        return null;
    }
}
