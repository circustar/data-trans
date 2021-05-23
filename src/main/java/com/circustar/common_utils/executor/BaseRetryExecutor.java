package com.circustar.common_utils.executor;

import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

public class BaseRetryExecutor<T> extends AbstractExecutor<T> implements IRetryExecutor<T> {
    public static int DEFAULT_RETRY_COUNT = 2;
    public static int DEFAULT_DELAY_SECONDS = 5;
    protected int retryCount;
    protected int delaySeconds;
    protected IExecutor executor;
    protected BiConsumer<T, Exception> retryExceptionConsumer = null;

    public BaseRetryExecutor(IExecutor executor) {
        this(executor, DEFAULT_RETRY_COUNT, DEFAULT_DELAY_SECONDS);
    }

    public BaseRetryExecutor(IExecutor executor
            , int retryCount, int delaySeconds) {
        this.executor = executor;
        this.retryCount = retryCount;
        this.delaySeconds = delaySeconds;
    }

    @Override
    public int getRetryCount() {
        return retryCount;
    }

    @Override
    public int getDelaySeconds() {
        return delaySeconds;
    }

    @Override
    public BaseRetryExecutor setRetryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    @Override
    public BaseRetryExecutor setDelaySeconds(int delaySeconds) {
        this.delaySeconds = delaySeconds;
        return this;
    }

    @Override
    public BiConsumer<T, Exception> getRetryErrorConsumer() {
        return this.retryExceptionConsumer;
    }

    @Override
    public IRetryExecutor setRetryErrorConsumer(BiConsumer<T, Exception> retryErrorConsumer) {
        this.retryExceptionConsumer = retryErrorConsumer;
        return this;
    }

    @Override
    public void process(T param)  {
        Exception ex = null;
        for(int i = 0 ; i < retryCount; i++) {
            try {
                this.executor.execute(param);
                return;
            } catch (Exception e) {
                ex = e;
                getRetryErrorConsumer().accept(param, e);
            }
            try {
                TimeUnit.SECONDS.sleep(delaySeconds);
            } catch (Exception exp) {
            }
        }
        if(ex != null) {
            throw new RuntimeException(ex);
        }
    }
}
