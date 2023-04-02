package com.demcia.eleven.core.feign;

import com.demcia.eleven.core.feign.exception.FeignServiceUnAvailableException;
import com.demcia.eleven.core.rest.dto.RestFailure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Order(1)
@ControllerAdvice
public class FeignExceptionAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(FeignServiceUnAvailableException.class)
    public RestFailure onException(FeignServiceUnAvailableException e) {
        return new RestFailure()
                .setMessage("服务器错误")
                .setError(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
    }


}
