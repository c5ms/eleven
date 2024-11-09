package com.eleven.core.configure;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@EnableCaching
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ElevenCacheConfiguration.class)
public @interface EnableElevenCache {

}
