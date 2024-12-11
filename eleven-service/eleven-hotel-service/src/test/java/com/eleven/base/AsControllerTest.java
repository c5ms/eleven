package com.eleven.base;


import com.eleven.core.configure.EnableElevenRest;
import com.eleven.core.configure.EnableElevenSecurity;

import java.lang.annotation.*;

/**
 * When we are testing the controller, we test:
 * <p>
 * 1. Receive the user input and validate it.
 * 2. Forward to service layer.
 * 3. Response the system output.
 */
@EnableElevenSecurity
@EnableElevenRest
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AsControllerTest {
}
