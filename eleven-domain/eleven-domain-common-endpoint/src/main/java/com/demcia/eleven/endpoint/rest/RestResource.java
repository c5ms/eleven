package com.demcia.eleven.endpoint.rest;

import org.springframework.stereotype.Indexed;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * 标记一个领域层资源终端
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@RestController
public @interface RestResource {
    /**
     * 版本（未实现）
     * TODO 终端层资源版本管理未实现
     *
     * @return 版本号
     */
    String version() default "";
}