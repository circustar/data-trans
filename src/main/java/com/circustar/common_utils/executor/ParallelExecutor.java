package com.circustar.common_utils.executor;

public class ParallelExecutor<T> extends AbstractExecutor<T>{

    protected IExecutor executor;
    public ParallelExecutor(IExecutor executor) {
        this.executor = executor;
    }

    @Override
    public void execute(T param) {
        new Thread(() -> {
            try {
                this.executor.execute(param);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }



}
