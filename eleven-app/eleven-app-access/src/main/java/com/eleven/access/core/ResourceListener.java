package com.eleven.access.core;

public interface ResourceListener extends Checkable {

    void start();

    void destroy();

    MessageChannel getChannel();

}
