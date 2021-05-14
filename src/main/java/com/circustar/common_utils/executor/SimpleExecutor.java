package com.circustar.common_utils.executor;

import java.util.function.Consumer;

public class SimpleExecutor<T> extends AbstractExecutor<T> {

    private Consumer<T> action;
    public SimpleExecutor(Consumer<T> action) {
        assert(action != null);
        this.action = action;
    }
    @Override
    public void execute(T param) throws Exception {
        action.accept(param);
    }
}
