package com.eleven.framework.domain;

public class NoDomainEntityException extends RuntimeException {

    private NoDomainEntityException() {
        super("No required principal can be found");
    }

    public static NoDomainEntityException instance() {
        return new NoDomainEntityException();
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
