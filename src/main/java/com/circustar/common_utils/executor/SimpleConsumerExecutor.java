package com.circustar.common_utils.executor;

import java.util.function.Consumer;

public class SimpleConsumerExecutor<T> extends AbstractExecutor<T> {

    private Consumer<T> action;
    public SimpleConsumerExecutor(Consumer<T> action) {
        assert(action != null);
        this.action = action;
    }
    @Override
    public void process(T param) throws Exception {
        action.accept(param);
    }
}
