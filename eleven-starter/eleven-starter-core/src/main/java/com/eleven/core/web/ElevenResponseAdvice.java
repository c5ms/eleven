package com.eleven.core.web;

import com.eleven.core.web.annonation.AsInnerApi;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

import java.util.Optional;


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

        if (selectedContentType.isCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON)) {
            return body;
        }

        // inner api return origin value as well
        // avoid not found error
        var innerApi = returnType.getDeclaringClass().getAnnotation(AsInnerApi.class);
        if (null != innerApi) {
            return body;
        }

        boolean isReturnNUll = null == body;
        if (body instanceof Optional<?>) {
            isReturnNUll = ((Optional<?>) body).isEmpty();
        }

        if (isReturnNUll && request.getMethod() == HttpMethod.GET) {
            response.setStatusCode(HttpStatus.NO_CONTENT);
            return null;
        }

        return body;
    }

}
