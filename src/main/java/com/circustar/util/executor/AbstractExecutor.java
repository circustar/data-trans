package com.circustar.util.executor;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class AbstractExecutor<T> implements IExecutor<T> {

    private Consumer<T> beforeExecuteConsumer;
    private Consumer<T> afterExecuteConsumer;
    private BiConsumer<T, Exception> errorConsumer;

    @Override
    public Consumer<T> getBeforeExecuteConsumer() {
        return this.beforeExecuteConsumer;
    }

    @Override
    public IExecutor setBeforeExecuteConsumer(Consumer<T> beforeExecuteConsumer) {
        this.beforeExecuteConsumer = beforeExecuteConsumer;
        return this;
    }

    @Override
    public Consumer<T> getAfterExecuteConsumer() {
        return this.afterExecuteConsumer;
    }

    @Override
    public IExecutor setAfterExecuteConsumer(Consumer<T> afterExecuteConsumer) {
        this.afterExecuteConsumer = afterExecuteConsumer;
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
