package com.eleven.access.core;

public interface MessageFilterFactory {
    String getName();

    MessageFilter apply(String configString);


}
