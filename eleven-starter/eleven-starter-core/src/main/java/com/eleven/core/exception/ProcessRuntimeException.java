package com.eleven.core.exception;

import lombok.Getter;

/**
 * 请求被拒绝异常，可能由于某种原因，逻辑无法继续正确执行下去。
 */
@Getter
public class ProcessRuntimeException extends RuntimeException implements ProcessError {
    public final String domain;
    public final String error;

    public ProcessRuntimeException(ProcessError error) {
        super(error.getMessage());
        this.domain = error.getDomain();
        this.error = error.getError();
    }

    public static ProcessRuntimeException of(ProcessError error) {
        return new ProcessRuntimeException(error);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
