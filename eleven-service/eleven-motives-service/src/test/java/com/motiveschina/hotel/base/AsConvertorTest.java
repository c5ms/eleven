package com.motiveschina.hotel.base;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
