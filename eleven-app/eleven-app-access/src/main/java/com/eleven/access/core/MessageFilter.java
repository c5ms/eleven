package com.eleven.access.core;

public interface MessageFilter {
    /**
     * 匹配消息
     *
     * @param exchange 要匹配的消息交换器
     * @return true 表示该消息匹配成功
     */
    boolean test(MessageExchange exchange);
}
