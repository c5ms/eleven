package com.demcia.eleven.core.rest.support;

import com.demcia.eleven.core.exception.DataNotFoundException;
import com.demcia.eleven.core.exception.PermissionDeadException;
import com.demcia.eleven.core.exception.ProcessFailureException;
import com.demcia.eleven.core.exception.UnauthorizedException;
import com.demcia.eleven.core.rest.dto.RestFailure;
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
        @ApiResponse(description = "需要认证授权", responseCode = "401"),
        @ApiResponse(description = "资源不存在", responseCode = "404"),
        @ApiResponse(description = "权限不足", responseCode = "403"),
        @ApiResponse(description = "处理失败", responseCode = "422"),
        @ApiResponse(description = "服务器错误", responseCode = "500"),
})
@ControllerAdvice
public class RestExceptionAdvice {

    // 需要认证授权 - 401
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public RestFailure onUnAuthenticatedException(UnauthorizedException e) {
        return new RestFailure()
                .setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }


    // 权限不足 - 403
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(PermissionDeadException.class)
    public RestFailure onPermissionDeadException(PermissionDeadException e) {
        return new RestFailure()
                .setError(HttpStatus.FORBIDDEN.getReasonPhrase());
    }


    // 资源不存在 404
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    public RestFailure onDataNotFoundException(DataNotFoundException e) {
        return new RestFailure()
                .setError(HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    // 校验失败 - 400
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public RestFailure onMethodArgumentNotValidException(BindException e) {
        var msg=e.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(StringUtils::isNotBlank)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.joining(";"));
        return new RestFailure()
                .setError(HttpStatus.BAD_REQUEST.getReasonPhrase())
                ;
    }

    // 请求被拒绝 - 422
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ProcessFailureException.class)
    public RestFailure onValidateFailureException(ProcessFailureException e) {
        return new RestFailure()
                .setMessage(StringUtils.defaultIfBlank(e.getMessage(), "服务器拒绝处理"))
                .setError(StringUtils.defaultIfBlank(e.getError(), "Failure"));
    }

    // 服务器错误
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public RestFailure onException(Exception e) {
        log.error("服务器错误",e);
        return new RestFailure()
                .setMessage(StringUtils.defaultIfBlank(e.getMessage(), "服务器错误"))
                .setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

}
