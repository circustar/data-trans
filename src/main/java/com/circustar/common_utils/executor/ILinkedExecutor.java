package com.circustar.common_utils.executor;

public interface ILinkedExecutor<T> extends IExecutor<T> {
    IExecutor<T> getExecutor();
    ILinkedExecutor<T> setExecutor(IExecutor<T> executor);
    
    IExecutor<T> getNextExecutor();
    ILinkedExecutor<T> setNextExecutor(IExecutor<T> executor);

    default void process(T param)  {
        getExecutor().execute(param);
        if(getNextExecutor()!= null) {
            getNextExecutor().execute(param);
        }
    }
}
