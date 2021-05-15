package com.circustar.common_utils.executor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class BaseParallelExecutor<T> extends BaseListExecutor<T> implements IParallelExecutor<T> {

    private boolean errorExit = false;
    private boolean successExit = true;
    private BiConsumer<Exception, IExecutor<T>> errorConsumer;
    private Consumer<IExecutor<T>> successConsumer;
    private boolean popElementOnSuccess = false;

    public BaseParallelExecutor() {
        super();
    }

    public BaseParallelExecutor(List<IExecutor<T>> executors) {
        super(executors);
    }

    public BaseParallelExecutor(IExecutor<T>... executors) {
        super(Arrays.stream(executors).collect(Collectors.toList()));
    }

    @Override
    public boolean onErrorExit() {
        return errorExit;
    }

    @Override
    public boolean getPopElementOnSuccess() {
        return this.popElementOnSuccess;
    }

    @Override
    public IParallelExecutor<T> setPopElementOnSuccess(boolean popElementOnSuccess) {
        this.popElementOnSuccess = popElementOnSuccess;
        return this;
    }


    @Override
    public IParallelExecutor setErrorExit(boolean errorExit) {
        this.errorExit = errorExit;
        return this;
    }


    @Override
    public BiConsumer<Exception, IExecutor<T>> getErrorConsumer() {
        return errorConsumer;
    }

    @Override
    public IParallelExecutor setErrorConsumer(BiConsumer<Exception, IExecutor<T>> errorConsumer) {
        this.errorConsumer = errorConsumer;
        return this;
    }

    @Override
    public boolean getSuccessExit() {
        return successExit;
    }

    @Override
    public IParallelExecutor setSuccessExit(boolean successExit) {
        this.successExit = successExit;
        return this;
    }

    @Override
    public Consumer<IExecutor<T>> getSuccessConsumer() {
        return successConsumer;
    }

    @Override
    public IParallelExecutor setSuccessConsumer(Consumer<IExecutor<T>> successConsumer) {
        this.successConsumer = successConsumer;
        return this;
    }

    @Override
    public void process(T param) throws Exception {
        List<IExecutor<T>> executors = getExecutors();
        List<IExecutor<T>> loopList = new ArrayList<>(executors);
        Exception outException = null;
        for(IExecutor executor : loopList) {
            try {
                executor.execute(param);
                Consumer<IExecutor<T>> successConsumer = getSuccessConsumer();
                if(successConsumer != null) {
                    successConsumer.accept(executor);
                }
                if(getPopElementOnSuccess()) {
                    this.moveExecutorFirst(executor);
                }
                if(getSuccessExit()) {
                    return;
                }
            } catch (Exception ex) {
                if(outException == null) {
                    outException = ex;
                }
                BiConsumer<Exception, IExecutor<T>> errorConsumer = getErrorConsumer();
                if(errorConsumer != null) {
                    errorConsumer.accept(ex, executor);
                }
                if(onErrorExit()) {
                    throw outException;
                }
            }
        }
        throw outException;
    }

}
