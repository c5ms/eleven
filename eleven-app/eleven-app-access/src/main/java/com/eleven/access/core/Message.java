package com.eleven.access.core;

import java.io.Serializable;
import java.util.Map;

/**
 * 消息定义,一个消息的生命周期是:创建-消费/拒绝-关闭
 */
public interface Message extends Serializable {

    /**
     * 获取消息头
     *
     * @return 消息头
     */
    Map<String, String> getHeader();

    /**
     * 消息主题
     *
     * @return 主题
     */
    String getTopic();

    /**
     * 获取字符集
     *
     * @return 字符集
     */
    String getCharset();

    /**
     * 将消息作为字符串
     *
     * @return 字符串内容
     */
    String asString() throws MessageException;

    /**
     * 将消息作为字节数组
     *
     * @return 字节数组
     */
    byte[] asBytes() throws MessageException;

    /**
     * 读取消息运行错误
     *
     * @return 消息错误
     */
    Throwable getError();

    /**
     * 标记消息出错
     *
     * @param throwable 错误
     */
    void setError(Throwable throwable);

    enum Direction {
        IN,
        OUT
    }

    enum State {
        ERRORED,
        SUCCESS
    }
}
