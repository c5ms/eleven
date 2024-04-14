package com.eleven.core.domain;

import lombok.Getter;

/**
 * 请求被拒绝异常，可能由于某种原因，逻辑无法继续正确执行下去。
 */
@Getter
public class DomainRuntimeException extends RuntimeException implements DomainError {
    public final String domain;
    public final String error;

    public DomainRuntimeException(DomainError error) {
        super(error.getMessage());
        this.domain = error.getDomain();
        this.error = error.getError();
    }

    public static DomainRuntimeException of(DomainError error) {
        return new DomainRuntimeException(error);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
