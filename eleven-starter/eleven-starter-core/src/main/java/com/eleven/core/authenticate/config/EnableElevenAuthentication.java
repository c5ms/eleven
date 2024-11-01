package com.eleven.core.authenticate.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ElevenAuthenticationConfigure.class)
public @interface EnableElevenAuthentication {

}
