package com.eleven.core.application.authorize;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypedObjectAuthorizer<C> implements ObjectAuthorizer {

    @Override
    public boolean support(Object resource) {
        Type handlerType = this.getClass().getGenericSuperclass();
        Type commandType = ((ParameterizedType) handlerType).getActualTypeArguments()[0];
        return resource.getClass().isAssignableFrom((Class<?>) commandType);
    }

    @SuppressWarnings("unchecked")
    private C cast(Object target) {
        return (C) target;
    }

    @Override
    public boolean isReadable(Object resource) {
        C c = this.cast(resource);
        return checkIsReadable(c);
    }


    @Override
    public boolean isWritable(Object resource) {
        C c = this.cast(resource);
        return checkIsWritable(c);
    }

    protected abstract boolean checkIsReadable(C c);

    protected  abstract boolean checkIsWritable(C c);
}
