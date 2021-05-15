package com.circustar.common_utils.executor;

public class EmptyConsumerExecutor<T> extends SimpleConsumerExecutor<T> {

    public EmptyConsumerExecutor() {
        super((param) -> {});
    }
}
