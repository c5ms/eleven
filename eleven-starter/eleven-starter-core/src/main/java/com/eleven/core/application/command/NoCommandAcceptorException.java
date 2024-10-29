package com.eleven.core.application.command;

public class NoCommandAcceptorException extends CommandException {

    public NoCommandAcceptorException(String message) {
        super(message);
    }

    public static NoCommandAcceptorException instance() {
        return new NoCommandAcceptorException("No such acceptor which the command can act");
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
