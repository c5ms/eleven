package com.demcia.eleven.core.application.rest;

import com.demcia.eleven.core.application.feign.exception.FeignServiceInvokedException;
import com.demcia.eleven.core.application.feign.exception.FeignServiceUnAvailableException;
import com.demcia.eleven.core.exception.ProcessErroredException;
import com.demcia.eleven.core.exception.ProcessRejectedException;
import com.demcia.eleven.core.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


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

    // 权限不足 - 403
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public RestFailure onAccessDenied(AccessDeniedException e) {
//        return new RestFailure()
//                .setError(HttpStatus.FORBIDDEN.getReasonPhrase());
        return null;
    }

    // 资源不存在 404
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public RestFailure onDataNotFoundException(ResourceNotFoundException e) {
//        return new RestFailure()
//                .setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        return null;
    }

    // 校验失败 - 400
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public RestFailure onMethodArgumentNotValidException(BindException e) {
//        var msg = e.getAllErrors().stream()
//                .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                .filter(StringUtils::isNotBlank)
//                .sorted(Comparator.naturalOrder())
//                .collect(Collectors.joining(";"));
//        return new RestFailure()
//                .setError(HttpStatus.BAD_REQUEST.getReasonPhrase())
//                ;
        return null;
    }

    // 请求被拒绝 - 422
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ProcessRejectedException.class)
    public RestFailure onProcessFailureException(ProcessRejectedException e) {
        return new RestFailure()
                .setMessage(StringUtils.defaultIfBlank(e.getMessage(), "服务器拒绝处理"))
                .setError(StringUtils.defaultIfBlank(e.getError(), "Failure"));
    }


    // Feign 服务不可用 - 503
    @ResponseBody
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(FeignServiceUnAvailableException.class)
    public RestFailure onException(FeignServiceUnAvailableException e) {
        log.error("内部微服务不可用", e);
//        return RestFailure.of(GlobalErrors.INTERNAL_ERROR);
        return null;
    }

    // Feign  服务调用错误 - 500
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FeignServiceInvokedException.class)
    public RestFailure onFeignServiceInvokedException(FeignServiceInvokedException e) {
        log.error("内部微服务调用异常,{}", e.getStatus(), e);
//        return RestFailure.of(GlobalErrors.INTERNAL_ERROR);
        return null;
    }


    // 请求出错误 - 500
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ProcessErroredException.class)
    public RestFailure onProcessErrorException(ProcessErroredException e) {
        log.error("内部错误", e);
//        return RestFailure.of(GlobalErrors.INTERNAL_ERROR);
        return null;
    }


    // 服务器错误 - 500
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public RestFailure onException(Exception e) {
        log.error("内部错误", e);
//        return RestFailure.of(GlobalErrors.INTERNAL_ERROR);
        return null;
    }

}
