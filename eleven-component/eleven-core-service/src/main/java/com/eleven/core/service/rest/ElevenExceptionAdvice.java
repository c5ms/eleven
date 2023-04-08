package com.eleven.core.service.rest;

import com.eleven.core.exception.ProcessErroredException;
import com.eleven.core.exception.ProcessRejectedException;
import com.eleven.core.exception.ResourceNotFoundException;
import feign.FeignException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
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
    public ProblemDetail onDataNotFoundException(ResourceNotFoundException e) {
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
    @ExceptionHandler
    public RestFailure onProcessFailureException(ProcessRejectedException e) {
        return new RestFailure()
                .setMessage(StringUtils.defaultIfBlank(e.getMessage(), "服务器拒绝处理"))
                .setError(StringUtils.defaultIfBlank(e.getError(), "Failure"));
    }



    // Feign  服务调用错误 - 500
    @ResponseBody
    @ExceptionHandler(FeignException.class)
    public ProblemDetail on(FeignException e) {
        log.error("内部微服务调用异常,{}", e.status(), e);
        var pd=  ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pd.setDetail("非法请求体");
        pd.setTitle("错误");
        return pd;
    }


    // 服务器错误 - 500
    @ResponseBody
    @ExceptionHandler
    public ProblemDetail onException(ProcessErroredException e) {
        log.error("内部错误", e);
        var pd=  ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pd.setDetail("内部错误");
        pd.setTitle("错误");
        return pd;
    }

}
