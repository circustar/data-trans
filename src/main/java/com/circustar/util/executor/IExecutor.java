package com.circustar.util.executor;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface IExecutor<T> {
    void execute(T param) throws Exception;
    Consumer<T> getBeforeExecuteConsumer();
    IExecutor setBeforeExecuteConsumer(Consumer<T> beforeExecuteConsumer);
    Consumer<T> getAfterExecuteConsumer();
    IExecutor setAfterExecuteConsumer(Consumer<T> afterExecuteConsumer);
    BiConsumer<T, Exception> getExecuteErrorConsumer();
    IExecutor setExecuteErrorConsumer(BiConsumer<T, Exception> executeErrorConsumer);
    default void executeFlow(T param) throws Exception
    {
        try {
            getBeforeExecuteConsumer().accept(param);
            execute(param);
            getAfterExecuteConsumer().accept(param);
        } catch (Exception ex) {
            getExecuteErrorConsumer().accept(param, ex);
            throw ex;
        }
    };
}
