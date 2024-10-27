package com.eleven.core.application.command;


public interface CommandHandler<Command, Result> {

    Result handle(Command command) throws CommandHandleException;

}
