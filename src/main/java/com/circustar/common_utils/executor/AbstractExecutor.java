package com.circustar.common_utils.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class AbstractExecutor<T> implements IExecutor<T> {

    private List<Consumer<T>> beforeExecuteConsumers;
    private List<Consumer<T>> afterExecuteConsumers;
    private BiConsumer<T, Exception> errorConsumer;

    public AbstractExecutor() {
        this.beforeExecuteConsumers = new ArrayList<>();
        this.afterExecuteConsumers = new ArrayList<>();
    }

    @Override
    public List<Consumer<T>> getBeforeExecuteConsumers() {
        return this.beforeExecuteConsumers;
    }

    @Override
    public IExecutor setBeforeExecuteConsumers(List<Consumer<T>> beforeExecuteConsumers) {
        this.beforeExecuteConsumers = beforeExecuteConsumers;
        return this;
    }

    @Override
    public List<Consumer<T>> getAfterExecuteConsumers() {
        return this.afterExecuteConsumers;
    }

    @Override
    public IExecutor setAfterExecuteConsumers(List<Consumer<T>> afterExecuteConsumers) {
        this.afterExecuteConsumers = afterExecuteConsumers;
        return this;
    }

    @Override
    public BiConsumer<T, Exception> getExecuteErrorConsumer() {
        return this.errorConsumer;
    }

    @Override
    public IExecutor setExecuteErrorConsumer(BiConsumer<T, Exception> executeErrorConsumer) {
        this.errorConsumer = executeErrorConsumer;
        return this;
    }
}
