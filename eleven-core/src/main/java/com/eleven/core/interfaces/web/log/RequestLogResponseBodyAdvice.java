package com.eleven.core.interfaces.web.log;

import cn.hutool.json.JSONUtil;
import com.eleven.core.application.query.PageResult;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Collections;

@Order(0)
@ControllerAdvice
@RequiredArgsConstructor
public class RequestLogResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(@Nonnull MethodParameter returnType, @Nonnull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  @Nonnull MethodParameter returnType,
                                  MediaType selectedContentType,
                                  @Nonnull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @Nonnull ServerHttpRequest request,
                                  @Nonnull ServerHttpResponse response) {
        RequestLog log = RequestLogContext.getLog();
        log.getResponse().setContentType(selectedContentType.toString());
        if (null != body) {
            if (body instanceof PageResult<?>) {
                log.getResponse().setBody(JSONUtil.toJsonStr(((PageResult<?>) body).withItems(Collections.emptyList())));
            } else {
                log.getResponse().setBody(JSONUtil.toJsonStr(body));
            }
        }
        return body;
    }

}
