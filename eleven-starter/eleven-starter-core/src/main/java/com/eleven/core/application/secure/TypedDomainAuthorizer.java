package com.eleven.core.application.secure;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypedDomainAuthorizer<C> implements DomainAuthorizer {

    @Override
    public boolean support(Object resource) {
        Type handlerType = this.getClass().getGenericSuperclass();
        Type commandType = ((ParameterizedType) handlerType).getActualTypeArguments()[0];
        return resource.getClass().isAssignableFrom((Class<?>) commandType);
    }

    @SuppressWarnings("unchecked")
    public C cast(Object target) {
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

    public abstract boolean checkIsReadable(C c);

    public abstract boolean checkIsWritable(C c);
}
