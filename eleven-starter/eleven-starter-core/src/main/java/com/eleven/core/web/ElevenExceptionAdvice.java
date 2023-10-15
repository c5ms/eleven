package com.eleven.core.web;

import com.eleven.core.constants.ElevenConstants;
import com.eleven.core.exception.DataNotFoundException;
import com.eleven.core.exception.ProcessRuntimeException;
import com.eleven.core.web.exception.ClientErrorException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
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


    // 校验失败 - 400
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BindException.class})
    public RestResponse.Failure on(BindException e) {
        var msg = e.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(StringUtils::isNotBlank)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.joining(";"));
        return RestResponse.Failure.of(ElevenConstants.ERROR_VALIDATE_FAILURE).setMessage(msg);
    }


    // 处理拒绝 - 422
    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public RestResponse.Failure on(ProcessRuntimeException e) {
        return RestResponse.Failure.of(e.getError());
    }


    // others
    @ResponseBody
    @ExceptionHandler({Exception.class,})
    public ResponseEntity<?> onNotSupport(Exception e) {
        HttpStatus status;

        // 403
        if (e instanceof AccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
        }
        //404
        else if (e instanceof NoHandlerFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (e instanceof DataNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        }

        //400
        else if (e instanceof HttpMediaTypeNotSupportedException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (e instanceof HttpMessageConversionException) {
            status = HttpStatus.BAD_REQUEST;
        }

        // customs client error
        else if (e instanceof ClientErrorException) {
            return ResponseEntity.status(((ClientErrorException) e).getStatus()).build();
        }
        // 500
        else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            log.error("内部错误", e);
        }

        return ResponseEntity.status(status).build();
    }


}
