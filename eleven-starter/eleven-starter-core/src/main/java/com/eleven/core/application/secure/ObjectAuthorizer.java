package com.eleven.core.application.secure;

public interface ObjectAuthorizer {

    boolean support(Object resource);

    boolean isReadable(Object resource);

    boolean isWritable(Object resource);

}
