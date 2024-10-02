package com.eleven.access.core;

public abstract class AbstractResourceProducer implements ResourceProducer {

    private volatile boolean isClosed = false;


    /**
     * 执行关闭
     */
    protected abstract void doClose();

    @Override
    public void close() {
        this.doClose();
        this.isClosed = true;
    }


    @Override
    public boolean isClosed() {
        return isClosed;
    }

}
