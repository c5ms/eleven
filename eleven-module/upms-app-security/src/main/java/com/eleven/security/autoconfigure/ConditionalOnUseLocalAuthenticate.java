package com.eleven.security.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnProperty(value = "eleven.security.auth-mode", havingValue = "local")
public @interface ConditionalOnUseLocalAuthenticate {


}