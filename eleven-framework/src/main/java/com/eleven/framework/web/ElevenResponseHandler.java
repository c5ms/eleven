package com.eleven.framework.web;

import com.eleven.framework.web.annonation.AsInnerApi;
import com.eleven.framework.web.model.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Optional;

@ControllerAdvice
@RequiredArgsConstructor
public class ElevenResponseHandler implements ResponseBodyAdvice<Object> {
    public static final String HTTP_HEADER_TOTAL_COUNT = "X-Total-Count";

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

        var statusCode = ((ServletServerHttpResponse) response).getServletResponse().getStatus();

        // decide to response 404 or 204
        if (statusCode == 404) {
            return body;
        }

        // inner api return origin value as well
        // avoid not found error
        var innerApi = returnType.getDeclaringClass().getAnnotation(AsInnerApi.class);
        if (null != innerApi) {
            return body;
        }

        if (request.getMethod() == HttpMethod.GET) {
            boolean isReturnNUll = null == body;
            if (body instanceof Optional<?>) {
                isReturnNUll = ((Optional<?>) body).isEmpty();
            }

            if (isReturnNUll && statusCode < 300 && statusCode >= 200) {
                response.setStatusCode(HttpStatus.NOT_FOUND);
                return null;
            }
        }

        if (body instanceof PageResponse<?> pageResponse) {
            response.getHeaders().set(HTTP_HEADER_TOTAL_COUNT, String.valueOf(pageResponse.getTotal()));
            body = pageResponse.getItems();
        }

        return body;
    }

}
