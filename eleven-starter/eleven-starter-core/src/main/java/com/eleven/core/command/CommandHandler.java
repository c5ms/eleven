package com.eleven.core.command;


public interface CommandHandler<Command, Result> {

    Result handle(Command command) throws CommandHandleException;

}
