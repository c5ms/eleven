package com.demcia.eleven.core.exception;

/**
 * 用于表示数据不存在
 */
public class DataNotFoundException extends RuntimeException {

    private DataNotFoundException(String message) {
        super(message);
    }

    public static DataNotFoundException of(String message) {
        return new DataNotFoundException(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

}
