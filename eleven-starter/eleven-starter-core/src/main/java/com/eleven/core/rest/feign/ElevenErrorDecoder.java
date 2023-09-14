package com.eleven.core.rest.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ElevenErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
//        var httpStatus = HttpStatus.valueOf(response.status());
//
//        // 服务不存在
//        if (httpStatus == HttpStatus.SERVICE_UNAVAILABLE) {
//            return new FeignServiceUnAvailableException(response.request());
//        }
//        // 服务不存在
//        if (httpStatus == HttpStatus.BAD_GATEWAY) {
//            return new FeignServiceUnAvailableException(response.request());
//        }
//
//        if (null != response.body()) {
//            try (InputStream is = response.body().asInputStream()) {
//                return new FeignServiceInvokedException(response.status(), IoUtil.read(is, response.charset()));
//            } catch (IOException e) {
//                log.error("feign error decode error", e);
//                return new RuntimeException(e);
//            }
//        }

        return defaultErrorDecoder.decode(methodKey, response);
    }

}
