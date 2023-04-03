package com.demcia.eleven.core.exception;

import com.demcia.eleven.core.codes.ElevenCode;

/**
 * 请求被拒绝异常，可能由于某种原因，逻辑无法继续正确执行下去。
 */
public class ProcessRejectedException extends RuntimeException {
    private final String error;

    private ProcessRejectedException(String error, String message) {
        super(message);
        this.error = error;
    }

    public static ProcessRejectedException of(String error, String message) {
        return new ProcessRejectedException(error, message);
    }

    public static ProcessRejectedException of(String message) {
        return new ProcessRejectedException(null, message);
    }

    public static ProcessRejectedException of(ElevenCode errorCode) {
        return new ProcessRejectedException(errorCode.getCode(), errorCode.getMessage());
    }

    public String getError() {
        return error;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
