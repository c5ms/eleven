package com.eleven.core.logs.annonation;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UseRequestLog {
    /**
     * 需要存储
     *
     * @return true 需要存储
     */
    boolean persist() default true;

    /**
     * 操作主题，通常是简短的概要
     *
     * @return 操作主题
     */
    String operate() default "";

    /**
     * 操作描述，通常是详细的说明
     *
     * @return 操作描述
     */
    String description() default "";
}
