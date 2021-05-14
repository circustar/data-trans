package com.circustar.common_utils.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface IExecutor<T> {
    static <T> IExecutor<T> createExecutor(Supplier<IExecutor<T>> supplier) {
        return supplier.get();
    }
    void execute(T param) throws Exception;
    List<Consumer<T>> getBeforeExecuteConsumers();
    IExecutor setBeforeExecuteConsumers(List<Consumer<T>> beforeExecuteConsumers);
    List<Consumer<T>> getAfterExecuteConsumers();
    IExecutor setAfterExecuteConsumers(List<Consumer<T>> afterExecuteConsumers);
    BiConsumer<T, Exception> getExecuteErrorConsumer();
    IExecutor setExecuteErrorConsumer(BiConsumer<T, Exception> executeErrorConsumer);
    default void executeFlow(T param) throws Exception
    {
        try {
            if(getBeforeExecuteConsumers() != null) {
                for(Consumer<T> consumer : getBeforeExecuteConsumers()) {
                    consumer.accept(param);
                }
            }
            execute(param);
            if(getAfterExecuteConsumers() != null) {
                for(Consumer<T> consumer : getAfterExecuteConsumers()) {
                    consumer.accept(param);
                }
            }
        } catch (Exception ex) {
            getExecuteErrorConsumer().accept(param, ex);
            throw ex;
        }
    }
    default IExecutor<T> addBeforeExecuteConsumer(Consumer<T> consumer) {
        if(getBeforeExecuteConsumers() == null) {
            setBeforeExecuteConsumers(new ArrayList<>());
        }
        getBeforeExecuteConsumers().add(consumer);
        return this;
    }
    default IExecutor<T> addAfterExecuteConsumer(Consumer<T> consumer) {
        if(getAfterExecuteConsumers() == null) {
            setAfterExecuteConsumers(new ArrayList<>());
        }
        getAfterExecuteConsumers().add(consumer);
        return this;
    }
}
