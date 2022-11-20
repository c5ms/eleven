package com.eleven.gateway.core.support;

/**
 * 默认的生命周期支持类<br>
 */
public abstract class AbstractLifecycle implements Lifecycle {

    private volatile boolean running = false;
    private volatile boolean stopped = false;

    /**
     * 执行启动操作
     *
     * @throws Exception 当启动失败的时候
     */
    protected abstract void doStart() throws Exception;

    /**
     * 执行停止操作,注意,这个方法会吞噬InterruptedException,然后保持挂起状态.
     *
     * @throws Exception 当启动失败的时候
     */
    protected abstract void doStop() throws Exception;

    /**
     * 启动生命周期,调用该方法后 isRunning() 立即变为true
     */
    @Override
    public synchronized void start() {
        if (!this.running) {
            try {
                this.stopped = false;
                this.running = true;
                this.doStart();
            } catch (Throwable e) {
                if (!this.stopped) {
                    this.running = false;
                    if (e instanceof RuntimeException) {
                        throw (RuntimeException) e;
                    }
                    throw new LifecycleException("对象启动异常", e);
                }
            }
        }
    }

    @Override
    public void stop() {
        if (this.running) {
            try {
                this.stopped = true;
                this.doStop();
                this.running = false;
            } catch (Exception e) {
                throw new LifecycleException("对象关闭异常", e);
            }
        }
    }

    /**
     * 查看生命周期是否处于启动状态
     *
     * @return 调用过启动, 但是没有调用过停止, 则返回true,一旦调用过停止方法,则返回false <br>
     */
    @Override
    public boolean isRunning() {
        return this.running && !this.stopped;
    }

    /**
     * 是否已经关闭
     *
     * @return 调用停止方法之后, 立即返回true
     */
    @Override
    public boolean isStopped() {
        return !isRunning();
    }
}
