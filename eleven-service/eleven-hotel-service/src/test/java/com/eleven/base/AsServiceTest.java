package com.eleven.base;


import java.lang.annotation.*;

/**
 * When we are testing the service, we test:
 * <p>
 * 1. Explain the command to domain layer
 * 2.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AsServiceTest {
}
