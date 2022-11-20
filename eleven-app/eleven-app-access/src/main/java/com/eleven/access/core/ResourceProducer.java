package com.eleven.access.core;

public interface ResourceProducer extends Checkable, AutoCloseable {

    /**
     * 发送消息
     *
     * @param message 要发送的消息
     * @throws Exception 当消息处理失败的时候
     */
    void produce(Message message) throws Exception;

    /**
     * 关闭
     */
    void close();

    /**
     * 是否已经关闭
     *
     * @return true 表示已关闭
     */
    boolean isClosed();


}
