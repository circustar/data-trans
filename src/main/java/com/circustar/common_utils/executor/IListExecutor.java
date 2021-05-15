package com.circustar.common_utils.executor;

import java.util.ArrayList;
import java.util.List;

public interface IListExecutor<T> extends IExecutor<T> {
    boolean getIgnoreNull();
    IListExecutor<T> setIgnoreNull(boolean ignoreNull);

    List<IExecutor<T>> getExecutors();
    IListExecutor setExecutors(List<IExecutor<T>> executors);
    default void process(T param) throws Exception {
        List<IExecutor> executors = new ArrayList<>(getExecutors());
        for(IExecutor e : executors) {
            e.execute(param);
        }
    }
    default IListExecutor<T> addExecutor(int index, IExecutor<T> iExecutor) {
        if(!getIgnoreNull() || iExecutor != null) {
            getExecutors().add(index, iExecutor);
        }
        return this;
    }
    default IListExecutor addExecutor(IExecutor<T> iExecutor) {
        if(getIgnoreNull() && iExecutor!= null) {
            getExecutors().add(iExecutor);
        }
        return this;
    }
    default IListExecutor<T> addFirstExecutor(IExecutor<T> iExecutor) {
        return this.addExecutor(0 ,iExecutor);
    }
    default IListExecutor<T> removeExecutor(IExecutor<T> iExecutor) {
        getExecutors().remove(iExecutor);
        return this;
    }

    default IListExecutor<T> removeExecutor(int index) {
        getExecutors().remove(index);
        return this;
    }

    default IListExecutor<T> removeFirstExecutor() {
        getExecutors().remove(0);
        return this;
    }

    default IListExecutor<T> removeLastExecutor() {
        int size = getExecutors().size();
        getExecutors().remove(size - 1);
        return this;
    }

    default IListExecutor<T> moveExecutor(IExecutor<T> executor, int targetPosition) {
        int index = getExecutors().indexOf(executor);
        if(targetPosition == index) {
            return this;
        }

        getExecutors().remove(executor);
        getExecutors().add(targetPosition, executor);

        return this;
    }

    default IListExecutor<T> moveExecutorFirst(IExecutor<T> executor) {
        return moveExecutor(executor, 0);
    }

    default IListExecutor<T> moveExecutorLast(IExecutor<T> executor) {
        return moveExecutor(executor, getExecutors().size() - 1);
    }
}
