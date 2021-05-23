package com.circustar.common_utils.executor;

import java.util.function.Predicate;

public interface ISkipExecutor<T> extends IExecutor<T> {
    ISkipExecutor<T> setSkipExpression(Predicate<T> predict);
    Predicate<T> getSkipExpression();

    IExecutor<T> getExecutor();
    ISkipExecutor<T> setExecutor(IExecutor<T> executor);

    default void process(T param)  {
        if(getExecutor() != null) {
            getExecutor().execute(param);
        }
    }

    @Override
    default void execute(T param)
    {
        if(getSkipExpression() != null && getSkipExpression().test(param)) {
            return;
        }
        IExecutor.super.execute(param);
    }
}
