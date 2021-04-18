package com.circustar.util.executor;

import java.util.function.BiConsumer;

public interface IRetryExecutor<T> extends IExecutor<T> {
    int getRetryCount();
    int getDelaySeconds();
    IRetryExecutor setRetryCount(int count);
    IRetryExecutor setDelaySeconds(int delaySeconds);
    BiConsumer<T, Exception> getRetryErrorConsumer();
    IRetryExecutor setRetryErrorConsumer(BiConsumer<T, Exception> retryErrorConsumer);
}
