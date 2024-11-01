package com.eleven.core.application.authorize;

public interface ObjectAuthorizer {

    boolean support(Object resource);

    boolean isReadable(Object resource);

    boolean isWritable(Object resource);

}
