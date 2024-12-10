package com.eleven.base;


import com.eleven.core.configure.EnableElevenRest;
import com.eleven.core.configure.EnableElevenSecurity;
import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.*;

@EnableElevenSecurity
@EnableElevenRest
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AsResourceTest {
}
