package com.eleven.core.application.authorize;

public class NoPrincipalException extends RuntimeException {

    public NoPrincipalException() {
        super("No required principal can be found");
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
