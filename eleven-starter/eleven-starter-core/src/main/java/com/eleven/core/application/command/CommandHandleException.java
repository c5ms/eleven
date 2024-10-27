package com.eleven.core.application.command;

public class CommandHandleException extends Exception {

    public CommandHandleException(String message) {
        super(message);
    }

    public CommandHandleException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandHandleException(Throwable cause) {
        super(cause);
    }
}
