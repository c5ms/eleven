package com.eleven.core.application.command;

public interface CommandValidator {

    boolean support(Object command);

    void validate(Object command) throws CommandValidateException;

}
