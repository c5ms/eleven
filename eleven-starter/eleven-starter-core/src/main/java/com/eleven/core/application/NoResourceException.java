package com.eleven.core.application;

public class NoResourceException extends RuntimeException {

    public NoResourceException(String message) {
        super(message);
    }

    public static NoResourceException instance() {
        return new NoResourceException("No such resource");
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
