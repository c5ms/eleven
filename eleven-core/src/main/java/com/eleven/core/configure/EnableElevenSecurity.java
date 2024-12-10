package com.eleven.core.configure;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ElevenSecurityConfigure.class, ElevenCoreConfigure.class})
public @interface EnableElevenSecurity {

}
