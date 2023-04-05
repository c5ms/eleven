package com.demcia.eleven.upms.application.strategy;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnProperty(prefix = "eleven.upms", name = "authenticate-mode", havingValue = "local", matchIfMissing = true)
public @interface ConditionalOnUseLocalAuthenticate {
}