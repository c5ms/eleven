package com.eleven.core.exception;

import lombok.Getter;

/**
 * 请求被拒绝异常，可能由于某种原因，逻辑无法继续正确执行下去。
 */
@Getter
public class ProcessRuntimeException extends RuntimeException {
    private final ProcessError error;

    public ProcessRuntimeException(ProcessError error) {
        super(error.getMessage());
        this.error = error;
    }

    public ProcessRuntimeException(ProcessError error, String message) {
        super(message);
        this.error = error;
    }

    public static ProcessRuntimeException of(ProcessError error) {
        return new ProcessRuntimeException(error);
    }
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
