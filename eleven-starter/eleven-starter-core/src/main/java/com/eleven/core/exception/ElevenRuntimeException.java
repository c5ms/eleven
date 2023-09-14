package com.eleven.core.exception;

import lombok.Getter;

/**
 * 请求被拒绝异常，可能由于某种原因，逻辑无法继续正确执行下去。
 */
@Getter
public class ElevenRuntimeException extends RuntimeException {
    private final ElevenError error;

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

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
