package com.eleven.access.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageErrors {
    INTERNAL_ERROR("INTERNAL_ERROR", "内部错误"),
    PERSIST_ERROR("PERSIST_ERROR", "持久化错误"),
    FILTER_ERROR("FILTER_ERROR", "过滤器组件错误"),
    NO_LOG_PARTITION("NO_LOG_PARTITION", "没有可用日志分区"),
    NO_SUCH_TOPIC("NO_SUCH_TOPIC", "主题不存在或未发布");

    final String error;
    final String message;

    /**
     * 使用指定消息生产一个异常
     *
     * @return 异常
     */
    public MessageException exception() {
        return new MessageException(this.getError(), this.getMessage());
    }

    /**
     * 使用指定消息生产一个异常
     *
     * @param message 消息
     * @return 异常
     */
    public MessageException exception(String message) {
        return new MessageException(this.getError(), message);
    }

    /**
     * 使用指定消息生产一个异常
     *
     * @param cause 造成的原因
     * @return 异常
     */
    public MessageException exception(Exception cause) {
        return new MessageException(getError(), getMessage(), cause);
    }


    /**
     * 使用指定消息生产一个异常
     *
     * @param cause 造成的原因
     * @return 异常
     */
    public MessageException exception(String message, Exception cause) {
        return new MessageException(getError(), message, cause);
    }


}
