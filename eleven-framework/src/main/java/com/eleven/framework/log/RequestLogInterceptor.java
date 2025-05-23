package com.eleven.framework.log;

import com.eleven.framework.security.SecurityContext;
import com.eleven.framework.log.annonation.UseRequestLog;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


@Slf4j
@Component
@RequiredArgsConstructor
public class RequestLogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) {
        if (handler instanceof HandlerMethod handlerMethod) {
            RequestLogContext.getLog().setOperation(StringUtils.substring(handlerMethod.getBeanType().getSimpleName() + "#" + handlerMethod.getMethod().getName(), 0, 200));
            RequestLogContext.setHandler(handlerMethod.getBeanType());

            Operation operation = handlerMethod.getMethodAnnotation(Operation.class);
            if (null != operation) {
                RequestLogContext.getLog().setOperation(operation.summary());
            }

            UseRequestLog useRequestLog = handlerMethod.getMethodAnnotation(UseRequestLog.class);
            if (null == useRequestLog) {
                useRequestLog = handlerMethod.getBeanType().getAnnotation(UseRequestLog.class);
            }
            if (null != useRequestLog) {
                if (StringUtils.isNotBlank(useRequestLog.operate())) {
                    RequestLogContext.getLog().setOperation(useRequestLog.operate());
                }
                if (useRequestLog.persist()) {
                    RequestLogContext.markPersist();
                }
            }

        }

        if (null != RequestLogContext.getHandler()) {
            var subject = SecurityContext.getCurrentSubject();
            var logger = LoggerFactory.getLogger(RequestLogContext.getHandler());
            logger.debug("request {} begin by {}",
                    RequestLogContext.getLog().getOperation(),
                    subject.getNickName()
            );
        }

        return true;
    }

    public void afterCompletion(@Nonnull HttpServletRequest request,
                                @Nonnull HttpServletResponse response,
                                @Nonnull Object handler,
                                @Nullable Exception ex) {
    }
}

