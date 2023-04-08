package com.eleven.core.exception;

/**
 * 用于表示数据不存在
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super("资源不存在");
    }

    private ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException of(String message) {
        return new ResourceNotFoundException(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

}
