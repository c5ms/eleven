package com.eleven.base;


import com.eleven.core.configure.EnableElevenCore;

import java.lang.annotation.*;

/**
 * When we are testing the convertor, we test:
 * <p>
 * 1. convert user input (request) into command
 * 2. convert domain object into Dto
 */
@EnableElevenCore
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AsConvertorTest {
}
