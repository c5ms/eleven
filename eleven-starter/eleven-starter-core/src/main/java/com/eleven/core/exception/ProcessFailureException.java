package com.eleven.core.exception;

import lombok.Getter;

/**
 * 请求被拒绝异常，可能由于某种原因，逻辑无法继续正确执行下去。
 */
@Getter
public class ProcessFailureException extends RuntimeException implements DomainError {

    public final String domain;
    public final String error;

    public ProcessFailureException(DomainError error) {
        super(error.getMessage());
        this.domain = error.getDomain();
        this.error = error.getError();
    }

    public ProcessFailureException(DomainError error, String message) {
        super(message);
        this.domain = error.getDomain();
        this.error = error.getError();
    }

    public static ProcessFailureException of(DomainError error) {
        return new ProcessFailureException(error);
    }

    public static ProcessFailureException of(DomainError error, String message) {
        return new ProcessFailureException(error, message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
