package com.eleven.core.application.command;

public class CommandValidateException extends Exception {

    public CommandValidateException(String message) {
        super(message);
    }

    public CommandValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandValidateException(Throwable cause) {
        super(cause);
    }
}
