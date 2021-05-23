package com.circustar.common_utils.executor;

public interface INodeExecutor<T> extends IExecutor<T> {
    IExecutor<T> getExecutor();
    INodeExecutor<T> setExecutor(IExecutor<T> executor);

    IExecutor<T> getBeforeExecutor();
    INodeExecutor<T> setBeforeExecutor(IExecutor<T> executor);

    IExecutor<T> getAfterExecutor();
    INodeExecutor<T> setAfterExecutor(IExecutor<T> executor);

    default void process(T param)  {
        if(getBeforeExecutor()!= null) {
            getBeforeExecutor().execute(param);
        }
        if(getExecutor() != null) {
            getExecutor().execute(param);
        }
        if(getAfterExecutor()!= null) {
            getAfterExecutor().execute(param);
        }
    }
}
