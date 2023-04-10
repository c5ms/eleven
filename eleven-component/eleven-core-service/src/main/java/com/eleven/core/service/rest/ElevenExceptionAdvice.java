package com.eleven.core.service.rest;

import com.eleven.core.exception.ProcessErroredException;
import com.eleven.core.exception.ProcessRejectedException;
import com.eleven.core.service.errors.GlobalErrors;
import com.eleven.core.service.rest.exception.ClientErrorException;
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

    // 权限不足 - 403
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail onAccessDenied() {
        var pd = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        pd.setTitle("权限不足");
        return pd;
    }

    // 校验失败 - 400
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ProblemDetail onMethodArgumentNotValidException(BindException e) {
        var msg = e.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(StringUtils::isNotBlank)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.joining(";"));
        var pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("校验失败");
        pd.setProperty("error", GlobalErrors.VALIDATE_FAILURE.getCode());
        pd.setProperty("fields",msg);
        return pd;
    }

    @ResponseBody
    @ExceptionHandler
    public ProblemDetail onProcessFailureException(ProcessRejectedException e) {
        var pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("请求无效");
        pd.setProperty("error", e.getError());
        return pd;
    }

//    @ResponseBody
//    @ExceptionHandler
//    public ProblemDetail onProcessErroredException(ProcessErroredException e) {
//        log.error("内部错误", e);
//        var pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//        pd.setTitle("内部错误");
//        return pd;
//    }

    @ResponseBody
    @ExceptionHandler(ClientErrorException.class)
    public ProblemDetail on(ClientErrorException e) {
        return ProblemDetail.forStatus(e.getStatus());
    }


    // 服务器错误 - 500
    @ResponseBody
    @ExceptionHandler
    public ProblemDetail onException(Exception e) {
        log.error("内部错误", e);
        var pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pd.setTitle("内部错误");
        return pd;
    }

}
