package com.circustar.common_utils.executor;

import java.util.function.Predicate;

public class BaseSkipExecutor<T> extends AbstractExecutor<T> implements ISkipExecutor<T> {
    private IExecutor<T> executor;
    private Predicate<T> predicate;

    @Override
    public ISkipExecutor<T> setSkipExpression(Predicate<T> predict) {
        this.predicate = predicate;
        return this;
    }

    @Override
    public Predicate<T> getSkipExpression() {
        return predicate;
    }

    @Override
    public IExecutor<T> getExecutor() {
        return executor;
    }

    @Override
    public BaseSkipExecutor<T> setExecutor(IExecutor<T> executor) {
        this.executor = executor;
        return this;
    }
}
