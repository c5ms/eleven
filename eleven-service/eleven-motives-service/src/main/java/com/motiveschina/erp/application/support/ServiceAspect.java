package com.motiveschina.erp.application.support;

import java.util.Optional;
import io.micrometer.observation.Observation;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * show you how to use aop to strength the class
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ServiceAspect {
    private final Tracer tracer;

    @Before("execution(* com.motiveschina.core.layer.ApplicationService+.*(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.debug("方法执行前: {}", joinPoint.getSignature());
    }

    @After("execution(* com.motiveschina.core.layer.ApplicationService+.*(..))")
    public void afterAdvice(JoinPoint joinPoint) {

        log.debug("方法执行后: {}", joinPoint.getSignature());
    }
}
