package com.demcia.eleven.core.rest.support;

import lombok.RequiredArgsConstructor;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.Predicate;


@RequiredArgsConstructor
public class RestApiPredicate implements Predicate<Class<?>> {

	private final List<String> packages;
	private final List<Class<? extends Annotation>> annotations;

	@Override
	public boolean test(Class<?> aClass) {

		for (String aPackage : packages) {
			if (aClass.getPackageName().startsWith(aPackage)) {
				return true;
			}
		}
		for (Class<? extends Annotation> annotatedInterface : annotations) {
			if (null != aClass.getAnnotation(annotatedInterface)) {
				return true;
			}
		}
		return false;
	}
}
