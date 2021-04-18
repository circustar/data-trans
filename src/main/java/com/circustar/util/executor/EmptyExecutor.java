package com.circustar.util.executor;

import java.util.Map;

public class EmptyExecutor<T> extends AbstractExecutor<T> {

    @Override
    public void execute(T param) throws Exception {
    }
}
