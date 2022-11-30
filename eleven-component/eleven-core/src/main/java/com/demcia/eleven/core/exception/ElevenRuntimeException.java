package com.demcia.eleven.core.exception;

/**
 * 用于表示数据不存在
 */
public class ElevenRuntimeException extends RuntimeException {

    public ElevenRuntimeException() {
    }

    public ElevenRuntimeException(String message) {
        super(message);
    }

    public ElevenRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElevenRuntimeException(Throwable cause) {
        super(cause);
    }

    public ElevenRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

}
