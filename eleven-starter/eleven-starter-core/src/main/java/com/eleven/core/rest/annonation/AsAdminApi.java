package com.eleven.core.rest.annonation;

import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * use to mark an endpoint as a backend-end API
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@RestController
public @interface AsAdminApi {

}
