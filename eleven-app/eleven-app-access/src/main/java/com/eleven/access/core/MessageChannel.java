package com.eleven.access.core;

/**
 * 消息处理器，自行实现对消息的处理，全局允许多个注册
 */
public interface MessageChannel {

    /**
     * 接收一个消息，如果消息处理失败，需要抛出一个异常，
     * 注意： 通常顶层分发消息的服务需要捕获这个异常，如果是事务性的消息，需要保证在抛出异常之后消息可以正常的处理失败的消息，这需要调用者来实现这个功能
     *
     * @param message 消息
     */
    void receive(Message message) throws Exception;

}
