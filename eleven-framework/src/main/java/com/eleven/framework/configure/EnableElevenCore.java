package com.eleven.framework.configure;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ ElevenCoreConfigure.class})
public @interface EnableElevenCore {

}
