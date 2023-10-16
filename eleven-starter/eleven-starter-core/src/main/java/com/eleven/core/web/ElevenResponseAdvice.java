package com.eleven.core.web;

import com.eleven.core.domain.PaginationResult;
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

        boolean isReturnNUll = null == body;
        if (body instanceof Optional<?>) {
            isReturnNUll = ((Optional<?>) body).isEmpty();
        }

        // 返回是 null，并且是 get 请求，则响应 404
        if (isReturnNUll && request.getMethod() == HttpMethod.GET) {
            response.setStatusCode(HttpStatus.NOT_FOUND);
            return null;
        }



        // 分页查询返回逻辑
//        if (body instanceof PaginationResult<?>) {
//            var total = ((PaginationResult<?>) body).getTotal();
//            response.getHeaders().set(RestConstants.HEADER_QUERY_TOTAL, String.valueOf(total));
//            return ((PaginationResult<?>) body).getItems();
//        }

        // 普通返回逻辑
//        if(!(body instanceof RestResponse)){
//            body= RestResponse.Success.of(body);
//        }

        return body;
    }

}
