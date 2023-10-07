package com.eleven.core.exception;

/**
 * 用于表示数据不存在
 */
public class RequireDataNotFoundException extends RuntimeException {

    public RequireDataNotFoundException() {
    }

    public RequireDataNotFoundException(String message) {
        super(message);
    }

    public RequireDataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequireDataNotFoundException(Throwable cause) {
        super(cause);
    }

    public RequireDataNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
