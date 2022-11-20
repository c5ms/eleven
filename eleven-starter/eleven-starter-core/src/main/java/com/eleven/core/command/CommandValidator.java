package com.eleven.core.command;

public interface CommandValidator {

    boolean support(Object command);

    void validate(Object command) throws CommandValidateException;

}
