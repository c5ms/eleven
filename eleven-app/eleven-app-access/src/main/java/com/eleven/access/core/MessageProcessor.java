package com.eleven.access.core;

public interface MessageProcessor {
    void filter(MessageExchange exchange, MessageProcessorChain chain);
}
