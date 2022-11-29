package com.demcia.eleven.core.feign;

import com.demcia.eleven.core.feign.exception.FeignFailureException;
import com.demcia.eleven.core.feign.exception.FeignServiceUnAvailableException;
import com.demcia.eleven.core.rest.RestfulFailure;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        var httpStatus = HttpStatus.valueOf(response.status());
        // 客户端错误
        if (httpStatus.is4xxClientError()) {
            for (String type : response.headers().get(HttpHeaders.CONTENT_TYPE)) {
                if (MediaType.parseMediaType(type).isCompatibleWith(MediaType.APPLICATION_JSON)) {
                    try (InputStream is = response.body().asInputStream()) {
                        var restApiFailureResult = objectMapper.readValue(is, RestfulFailure.class);
                        throw new FeignFailureException(HttpStatus.valueOf(response.status()), restApiFailureResult);
                    } catch (IOException e) {
                        log.error("feign error decode error", e);
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        // 服务端错误
        if (httpStatus.is5xxServerError()) {

            // 服务不存在
            if (httpStatus == HttpStatus.SERVICE_UNAVAILABLE) {
                throw new FeignServiceUnAvailableException(response.request());
            }
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }

}