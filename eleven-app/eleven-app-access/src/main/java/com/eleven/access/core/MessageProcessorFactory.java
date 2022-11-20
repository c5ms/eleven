package com.eleven.access.core;

public interface MessageProcessorFactory {

    MessageProcessor apply(String configString);

    String getName();
}
