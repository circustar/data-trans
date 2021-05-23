package com.circustar.common_utils.executor;

public class BaseNodeExecutor<T> extends AbstractExecutor<T> implements INodeExecutor<T> {
    private IExecutor<T> executor;
    private IExecutor<T> beforeExecutor;
    private IExecutor<T> afterExecutor;

    @Override
    public IExecutor<T> getExecutor() {
        return executor;
    }

    @Override
    public INodeExecutor<T> setExecutor(IExecutor<T> executor) {
        this.executor = executor;
        return this;
    }

    @Override
    public IExecutor<T> getBeforeExecutor() {
        return this.beforeExecutor;
    }

    @Override
    public INodeExecutor<T> setBeforeExecutor(IExecutor<T> beforeExecutor) {
        this.beforeExecutor = beforeExecutor;
        return this;
    }

    @Override
    public IExecutor<T> getAfterExecutor() {
        return this.afterExecutor;
    }

    @Override
    public INodeExecutor<T> setAfterExecutor(IExecutor<T> afterExecutor) {
        this.afterExecutor = afterExecutor;
        return this;
    }
}
