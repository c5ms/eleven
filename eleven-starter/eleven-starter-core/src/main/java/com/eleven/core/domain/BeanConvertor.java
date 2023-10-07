package com.eleven.core.domain;

public interface BeanConvertor {

    <T> T to(Object source, Class<T> targetClass);
}
