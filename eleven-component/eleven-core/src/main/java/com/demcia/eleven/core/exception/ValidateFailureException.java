package com.demcia.eleven.core.exception;

public class ValidateFailureException extends RuntimeException {

    public ValidateFailureException(String message) {
        super(message);
    }

    public ValidateFailureException() {
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

}
