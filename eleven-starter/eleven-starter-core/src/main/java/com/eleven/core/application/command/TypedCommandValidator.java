package com.eleven.core.application.command;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypedCommandValidator<C> implements CommandValidator {

    @Override
    public boolean support(Object command) {
        Type handlerType = this.getClass().getGenericSuperclass();
        Type commandType = ((ParameterizedType) handlerType).getActualTypeArguments()[0];
        return command.getClass().isAssignableFrom((Class<?>) commandType);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void validate(Object command) {
        C aCommand = (C) command;
        doAuthorize(aCommand);
    }

    protected abstract void doAuthorize(C command);
}
