package com.eleven.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 请求被拒绝异常，可能由于某种原因，逻辑无法继续正确执行下去。
 */
public class ElevenRuntimeException extends RuntimeException {
    private final ElevenError error;

    private ElevenRuntimeException(String error, String message) {
        super(message);
        this.error = new DefaultElevenError(error, message);
    }

    public ElevenRuntimeException(ElevenError error) {
        super(error.getMessage());
        this.error = error;
    }

    public ElevenRuntimeException(ElevenError error, String message) {
        super(message);
        this.error = error;
    }

    public static ElevenRuntimeException of(ElevenError error) {
        return new ElevenRuntimeException(error);
    }

    public static ElevenRuntimeException of(ElevenError error, String message) {
        return new ElevenRuntimeException(error, message);
    }

    public ElevenError getError() {
        return error;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    @Getter
    @AllArgsConstructor
    public static class DefaultElevenError implements ElevenError {
        private String error;
        private String message;
    }
}
