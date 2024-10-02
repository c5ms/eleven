package com.eleven.access.core;

public interface ResourceConsumer {

    void listen(MessageChannel processor);

    boolean checkHealth() throws Exception;

    void destroy();
}
