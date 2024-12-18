package com.eleven.travel.base;


import java.lang.annotation.*;

/**
 * When we are testing the service, we test:
 * <p>
 * 1. Explain the command to domain layer
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AsServiceTest {
}
