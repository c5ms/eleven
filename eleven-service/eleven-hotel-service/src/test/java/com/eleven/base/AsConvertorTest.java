package com.eleven.base;


import com.eleven.core.configure.EnableElevenCore;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

//@Import({
//    JacksonAutoConfiguration.class
//})
@EnableElevenCore
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AsConvertorTest {
}
