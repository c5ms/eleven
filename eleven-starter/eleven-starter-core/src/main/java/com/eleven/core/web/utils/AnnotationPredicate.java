package com.eleven.core.web.utils;

import lombok.RequiredArgsConstructor;

import java.lang.annotation.Annotation;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class AnnotationPredicate implements Predicate<Class<?>> {

    private final Class<? extends Annotation> annotation;

    @Override
    public boolean test(Class<?> aClass) {
        return null != aClass.getAnnotation(annotation);
    }
}
