package com.eleven.core.command.support;

import com.eleven.core.command.CommandHandleException;
import com.eleven.core.command.CommandHandler;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiredArgsConstructor
public class MethodCommandHandlerAdapter implements CommandHandler<Object, Object> {

    private final Object target;
    private final Method method;

    @Override
    public Object handle(Object command) throws CommandHandleException {
        try {
            return method.invoke(target, command);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new CommandHandleException("error wil handle the command", e);
        }
    }
}
