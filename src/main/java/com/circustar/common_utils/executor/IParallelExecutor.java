package com.circustar.common_utils.executor;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface IParallelExecutor<T> extends IListExecutor<T> {
    boolean onErrorExit();
    IParallelExecutor<T> setErrorExit(boolean errorExit);
    boolean getPopElementOnSuccess();
    IParallelExecutor<T> setPopElementOnSuccess(boolean popElementOnSuccess);
    BiConsumer<Exception, IExecutor<T>> getErrorConsumer();
    IParallelExecutor<T> setErrorConsumer(BiConsumer<Exception, IExecutor<T>> errorConsumer);
    boolean getSuccessExit();
    IParallelExecutor<T> setSuccessExit(boolean successExit);
    Consumer<IExecutor<T>> getSuccessConsumer();
    IParallelExecutor setSuccessConsumer(Consumer<IExecutor<T>> successConsumer);
}
