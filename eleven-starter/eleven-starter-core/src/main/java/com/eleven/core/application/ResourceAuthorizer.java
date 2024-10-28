package com.eleven.core.application;

public interface ResourceAuthorizer {

    boolean support(Object resource);

    boolean isReadable(Object resource);

    boolean isWritable(Object resource);

}
