package com.eleven.framework.configure;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableElevenCore
@EnableElevenDomain
@EnableElevenRest
@EnableElevenSecurity
@EnableElevenLog
public @interface EnableElevenFramework {
}
