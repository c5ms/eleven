package com.eleven.core.application.security;

import com.eleven.core.application.command.CommandException;

public class NoPrincipalException extends CommandException {

    public NoPrincipalException(String message) {
        super(message);
    }

    public static NoPrincipalException instance() {
        return new NoPrincipalException("No principal can be found");
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
