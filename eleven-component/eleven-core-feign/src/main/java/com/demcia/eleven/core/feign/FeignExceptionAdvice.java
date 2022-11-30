package com.demcia.eleven.core.feign;

import com.demcia.eleven.core.feign.exception.FeignServiceUnAvailableException;
import com.demcia.eleven.core.rest.RestfulFailure;
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
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FeignServiceUnAvailableException.class)
    public RestfulFailure onException(FeignServiceUnAvailableException e) {
        return new RestfulFailure()
                .setMessage("服务器错误")
                .setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }


}
