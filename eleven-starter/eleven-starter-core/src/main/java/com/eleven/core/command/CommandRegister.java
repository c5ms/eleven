package com.eleven.core.command;

public interface CommandRegister {

    <Command, Result> void register(CommandHandler<?, ?> commandHandler, Class<Command> commandClass, Class<Result> resultClass);
}
