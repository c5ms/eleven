package com.eleven.core.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Order(1)
@ControllerAdvice
@RequiredArgsConstructor
public class ElevenResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(@NonNull MethodParameter returnType,
                            @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  @NonNull MethodParameter returnType,
                                  @NonNull MediaType selectedContentType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NonNull ServerHttpRequest request,
                                  @NonNull ServerHttpResponse response) {

        // 返回是 null，并且是 get 请求，则响应 404
        if (null == body && request.getMethod() == HttpMethod.GET) {
            response.setStatusCode(HttpStatus.NOT_FOUND);
        }

        // 分页查询返回逻辑
//        if (body instanceof QueryResult<?>) {
//            var total = ((QueryResult<?>) body).getTotal();
//            response.getHeaders().set("X-Query-Total", String.valueOf(total));
//            return ((QueryResult<?>) body).getItems();
//        }


        return body;
    }

}
