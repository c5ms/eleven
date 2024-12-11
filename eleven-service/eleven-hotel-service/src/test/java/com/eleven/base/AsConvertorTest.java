package com.eleven.base;


import java.lang.annotation.*;

/**
 * When we are testing the convertor, we test:
 * <p>
 * 1. convert user input (request) into command
 * 2. convert domain object into Dto
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AsConvertorTest {
}
