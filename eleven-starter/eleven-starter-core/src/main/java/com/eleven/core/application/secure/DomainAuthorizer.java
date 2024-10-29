package com.eleven.core.application.secure;

public interface DomainAuthorizer {

    boolean support(Object resource);

    boolean isReadable(Object resource);

    boolean isWritable(Object resource);

}
