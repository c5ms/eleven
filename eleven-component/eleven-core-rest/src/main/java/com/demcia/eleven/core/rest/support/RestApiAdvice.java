package com.demcia.eleven.core.rest.support;

import com.demcia.eleven.core.exception.DataNotFoundException;
import com.demcia.eleven.core.exception.PermissionDeadException;
import com.demcia.eleven.core.exception.ValidateFailureException;
import com.demcia.eleven.core.rest.RestfulFailure;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Comparator;
import java.util.stream.Collectors;

@Slf4j
@ApiResponses({
        @ApiResponse(description = "资源不存在", responseCode = "404"),
        @ApiResponse(description = "权限不足", responseCode = "403"),
        @ApiResponse(description = "校验失败", responseCode = "422"),
        @ApiResponse(description = "服务器错误", responseCode = "500"),
})
@ControllerAdvice
public class RestApiAdvice {

    // 资源不存在 404
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    public RestfulFailure onDataNotFoundException(DataNotFoundException e) {
        return new RestfulFailure()
                .setMessage(StringUtils.defaultIfBlank(e.getMessage(), "资源不存在"))
                .setError(HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    // 权限不足 - 403
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(PermissionDeadException.class)
    public RestfulFailure onPermissionDeadException(PermissionDeadException e) {
        return new RestfulFailure()
                .setMessage(StringUtils.defaultIfBlank(e.getMessage(), "权限不足"))
                .setError(HttpStatus.FORBIDDEN.getReasonPhrase());
    }

    // 校验失败 - 422
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ValidateFailureException.class)
    public RestfulFailure onValidateFailureException(ValidateFailureException e) {
        return new RestfulFailure()
                .setMessage(StringUtils.defaultIfBlank(e.getMessage(), "业务校验失败"))
                .setError(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase());
    }

    // 校验失败 - 422
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(BindException.class)
    public RestfulFailure onMethodArgumentNotValidException(BindException e) {
                String message = e.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(StringUtils::isNotBlank)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.joining(";"));
        return new RestfulFailure()
                .setMessage(message)
                .setError(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase());
    }

    // 服务器错误 - 500
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public RestfulFailure onException(Exception e) {
        log.error("服务器错误", e);
        return new RestfulFailure()
                .setMessage("服务器错误")
                .setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

}
