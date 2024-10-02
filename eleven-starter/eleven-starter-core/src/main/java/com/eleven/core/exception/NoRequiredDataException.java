package com.eleven.core.exception;

/**
 * 用于表示数据不存在
 */
public class NoRequiredDataException extends RuntimeException {

    public NoRequiredDataException() {
    }

    public NoRequiredDataException(String message) {
        super(message);
    }

    public NoRequiredDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRequiredDataException(Throwable cause) {
        super(cause);
    }

    public NoRequiredDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
