package com.motiveschina.hotel.base;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * When we are testing the controller, we test:
 * <p>
 * 1. Receive the user input and validate it.
 * 2. Forward to service layer.
 * 3. Response the system output.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AsControllerTest {
}
