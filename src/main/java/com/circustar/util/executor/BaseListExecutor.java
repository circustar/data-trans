package com.circustar.util.executor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseListExecutor<T> extends AbstractExecutor<T> implements IListExecutor<T> {

    boolean ignoreNull = true;

    protected List<IExecutor<T>> executors;

    public BaseListExecutor() {
        this(new ArrayList<IExecutor<T>>());
    }

    public BaseListExecutor(List<IExecutor<T>> executors) {
        this.executors = executors;
    }

    public BaseListExecutor(IExecutor<T>... executors) {
        this(Arrays.asList(executors));
    }

    @Override
    public boolean getIgnoreNull() {
        return ignoreNull;
    }

    @Override
    public IListExecutor<T> setIgnoreNull(boolean ignoreNull) {
        this.ignoreNull = ignoreNull;
        return this;
    }


    @Override
    public List<IExecutor<T>> getExecutors() {
        return this.executors;
    }

    @Override
    public IListExecutor setExecutors(List<IExecutor<T>> executors) {
        this.executors = executors;
        return this;
    }
}
