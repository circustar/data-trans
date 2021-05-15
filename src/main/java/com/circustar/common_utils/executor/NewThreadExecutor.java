package com.circustar.common_utils.executor;

public class NewThreadExecutor<T> extends AbstractExecutor<T>{

    protected IExecutor executor;
    public NewThreadExecutor(IExecutor executor) {
        this.executor = executor;
    }

    @Override
    public void process(T param) {
        new Thread(() -> {
            try {
                this.executor.execute(param);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }
}
