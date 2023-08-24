package com.eleven.core.rest;

import com.eleven.core.exception.ElevenRuntimeException;
import com.eleven.core.rest.exception.ClientErrorException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Comparator;
import java.util.stream.Collectors;


@Slf4j
@ApiResponses({
        @ApiResponse(description = "请求无效", responseCode = "400"),
        @ApiResponse(description = "权限不足", responseCode = "403"),
        @ApiResponse(description = "无此资源", responseCode = "404"),
        @ApiResponse(description = "处理失败", responseCode = "422"),
        @ApiResponse(description = "内部错误", responseCode = "500")
})
@ControllerAdvice
public class ElevenExceptionAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public void notfound() {

    }

    // 权限不足 - 403
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public void on() {

    }

    // 校验失败 - 400
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public RestResponse.Failure on(BindException e) {
        var msg = e.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(StringUtils::isNotBlank)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.joining(";"));

//        return RestResponse.Failure.of(RestErrors.VALIDATE_FAILURE).setMessage(msg);
        return RestResponse.Failure.of(RestErrors.VALIDATE_FAILURE);
    }


    // 处理拒绝 - 422
    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public RestResponse.Failure on(ElevenRuntimeException e) {
        return RestResponse.Failure.of(e.getError());
    }


    @ResponseBody
    @ExceptionHandler(ClientErrorException.class)
    public ResponseEntity<?> on(ClientErrorException e) {
        return ResponseEntity.status(e.getStatus()).build();
    }


    // 服务器错误 - 500
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public void on(Exception e) {
        log.error("内部错误", e);
    }

}
