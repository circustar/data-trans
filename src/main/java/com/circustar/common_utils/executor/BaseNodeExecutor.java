package com.circustar.common_utils.executor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BaseNodeExecutor<T> extends AbstractExecutor<T> implements INodeExecutor<T> {
    private IExecutor<T> executor;
    private IExecutor<T> subExecutor;
    private boolean executeSubNodeFirst = false;

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
    public IExecutor<T> getSubExecutor() {
        return this.subExecutor;
    }

    @Override
    public INodeExecutor<T> setSubExecutor(IExecutor<T> executor) {
        this.subExecutor = subExecutor;
        return this;
    }

    @Override
    public boolean getExecuteSubNodeFirst() {
        return executeSubNodeFirst;
    }

    @Override
    public INodeExecutor<T> setExecuteSubNodeFirst(boolean executeSubNodeFirst) {
        this.executeSubNodeFirst = executeSubNodeFirst;
        return this;
    }
}
