package com.circustar.util.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface ISuccessExitListExecutor<T> extends IListExecutor<T> {
    boolean getErrorExit();
    ISuccessExitListExecutor<T> setErrorExit(boolean errorExit);
    boolean getPopElementOnSuccess();
    ISuccessExitListExecutor<T> setPopElementOnSuccess(boolean popElementOnSuccess);
    BiConsumer<Exception, IExecutor<T>> getErrorConsumer();
    ISuccessExitListExecutor<T> setErrorConsumer(BiConsumer<Exception, IExecutor<T>> errorConsumer);
    boolean getSuccessExit();
    ISuccessExitListExecutor<T> setSuccessExit(boolean successExit);
    Consumer<IExecutor<T>> getSuccessConsumer();
    ISuccessExitListExecutor setSuccessConsumer(Consumer<IExecutor<T>> successConsumer);
}
