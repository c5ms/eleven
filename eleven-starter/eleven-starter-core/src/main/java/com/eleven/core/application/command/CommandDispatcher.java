package com.eleven.core.application.command;

public interface CommandDispatcher {

    <C, R> R dispatch(C c, Class<R> resultClass) throws CommandHandleException, CommandInvalidException;

    default <C> void dispatch(C c) throws CommandHandleException, CommandInvalidException {
        dispatch(c, void.class);
    }

}
