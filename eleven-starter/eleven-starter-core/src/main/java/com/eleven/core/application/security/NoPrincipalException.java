package com.eleven.core.application.security;

import com.eleven.core.application.command.CommandException;

public class NoPrincipalException extends CommandException {

    public NoPrincipalException() {
        super("No required principal can be found");
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
