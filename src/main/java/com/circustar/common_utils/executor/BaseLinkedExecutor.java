package com.circustar.common_utils.executor;

public class BaseLinkedExecutor<T> extends AbstractExecutor<T> implements ILinkedExecutor<T> {
    private IExecutor<T> executor;
    private IExecutor<T> nextExecutor;

    public BaseLinkedExecutor(IExecutor<T> executor) {
        this.executor = executor;
    }

    @Override
    public IExecutor<T> getExecutor() {
        return executor;
    }

    @Override
    public ILinkedExecutor<T> setExecutor(IExecutor<T> executor) {
        this.executor = executor;
        return this;
    }

    @Override
    public IExecutor<T> getNextExecutor() {
        return this.nextExecutor;
    }

    @Override
    public ILinkedExecutor<T> setNextExecutor(IExecutor<T> nextExecutor) {
        this.nextExecutor = nextExecutor;
        return this;
    }
}
