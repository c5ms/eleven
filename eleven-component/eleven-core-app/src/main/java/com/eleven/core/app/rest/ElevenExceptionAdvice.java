package com.eleven.core.app.rest;

import com.eleven.core.exception.ProcessRejectedException;
import com.eleven.core.app.errors.CoreError;
import com.eleven.core.app.rest.exception.ClientErrorException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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

    @ResponseBody
    @ExceptionHandler(ClientErrorException.class)
    public ProblemDetail on(ClientErrorException e) {
        return ProblemDetail.forStatus(e.getStatus());
    }



    // 权限不足 - 403
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public void onAccessDenied() {

    }

    // 校验失败 - 400
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public RestFailure onMethodArgumentNotValidException(BindException e) {
        var msg = e.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(StringUtils::isNotBlank)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.joining(";"));
        return RestFailure.of(CoreError.VALIDATE_FAILURE).setMessage(msg);
    }

    // 处理拒绝 - 422
    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public RestFailure onProcessFailureException(ProcessRejectedException e) {
        return RestFailure.of(e.getError(), e.getMessage());
    }



    // 服务器错误 - 500
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public void onException(Exception e) {
        log.error("内部错误", e);
    }

}
