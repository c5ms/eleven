package com.eleven.core.web.annonation;

import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@RestController
public @interface AsAdminApi {

}
