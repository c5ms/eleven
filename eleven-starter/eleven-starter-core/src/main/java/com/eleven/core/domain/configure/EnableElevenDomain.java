package com.eleven.core.domain.configure;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@EnableCaching
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ElevenDomainConfigure.class)
public @interface EnableElevenDomain {

}
