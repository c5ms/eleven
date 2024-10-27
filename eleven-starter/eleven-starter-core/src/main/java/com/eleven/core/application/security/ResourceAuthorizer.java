package com.eleven.core.application.security;

public interface ResourceAuthorizer {

    boolean support(Object resource);

    boolean isReadable(Object resource) throws NoWritePermissionException, NoAccessPermissionException;

    boolean isWritable(Object resource) throws NoWritePermissionException, NoAccessPermissionException;
}
