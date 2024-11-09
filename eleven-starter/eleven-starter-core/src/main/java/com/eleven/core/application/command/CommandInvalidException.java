package com.eleven.core.application.command;

public class CommandInvalidException extends CommandException {

    public CommandInvalidException(String message) {
        super(message);
    }

    public CommandInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandInvalidException(Throwable cause) {
        super(cause);
    }
}
