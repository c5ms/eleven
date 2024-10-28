package com.eleven.core.application;

public class NoPermissionException extends RuntimeException {

    public NoPermissionException(String message) {
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

}
