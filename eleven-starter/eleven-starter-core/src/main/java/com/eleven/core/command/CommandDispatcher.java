package com.eleven.core.command;

public interface CommandDispatcher {

    <C, R> R dispatch(C c, Class<R> resultClass) throws CommandHandleException, CommandValidateException;

    default <C> void dispatch(C c) throws CommandHandleException, CommandValidateException {
        dispatch(c, void.class);
    }

}
