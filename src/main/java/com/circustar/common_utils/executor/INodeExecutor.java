package com.circustar.common_utils.executor;

public interface INodeExecutor<T> extends IExecutor<T> {
    IExecutor<T> getExecutor();
    INodeExecutor<T> setExecutor(IExecutor<T> executor);

    IExecutor<T> getSubExecutor();
    INodeExecutor<T> setSubExecutor(IExecutor<T> executor);

    boolean getExecuteSubNodeFirst();
    INodeExecutor<T> setExecuteSubNodeFirst(boolean executeSubNodeFirst);

    default void process(T param)  {
        if(getExecuteSubNodeFirst()) {
            if(getExecutor()!= null) {
                getExecutor().execute(param);
            }
        }
        if(getSubExecutor() != null) {
            getSubExecutor().execute(param);
        }
        if(!getExecuteSubNodeFirst()) {
            if(getExecutor()!= null) {
                getExecutor().execute(param);
            }
        }
    }
}
