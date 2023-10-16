package com.eleven.core.web;

import com.eleven.core.constants.ElevenConstants;
import com.eleven.core.exception.DataNotFoundException;
import com.eleven.core.exception.ProcessError;
import com.eleven.core.exception.ProcessRuntimeException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.URI;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@ApiResponses({
        @ApiResponse(description = "处理失败", responseCode = "400"),
        @ApiResponse(description = "权限不足", responseCode = "403"),
        @ApiResponse(description = "地址错误", responseCode = "404"),
        @ApiResponse(description = "格式错误", responseCode = "415"),
        @ApiResponse(description = "方法错误", responseCode = "405"),
        @ApiResponse(description = "内部错误", responseCode = "500")
})
@ControllerAdvice
public class ElevenExceptionAdvice {

    // others
    @ResponseBody
    @ExceptionHandler({Exception.class,})
    public ResponseEntity<ProblemDetail> on(Exception e) {
        HttpStatus status;

        //400 - payload
        if (e instanceof HttpMessageConversionException) {
            return ResponseEntity.of(createProblemDetails(ElevenConstants.ERROR_PAYLOAD_ERROR)).build();
        }

        // 400 -  process
        else if (e instanceof ProcessRuntimeException) {
            return ResponseEntity.of(createProblemDetails((ProcessError) e)).build();
        }
        // 400 - bind error
        else if (e instanceof BindException ex) {
            return renderBindException(ex);
        }

        // 403
        else if (e instanceof AccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
        }

        //404
        else if (e instanceof NoHandlerFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (e instanceof DataNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        }

        // 415 405
        else if (e instanceof HttpMediaTypeNotSupportedException) {
            status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            status = HttpStatus.METHOD_NOT_ALLOWED;
        }

        // 500
        else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            log.error("内部错误", e);
        }

        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setTitle( status.getReasonPhrase());
        return ResponseEntity.status(status).body(problemDetail);
    }

    private ResponseEntity<ProblemDetail> renderBindException(BindException ex) {
        var problemDetails = createProblemDetails(ElevenConstants.ERROR_VALIDATE_FAILURE);
        problemDetails.setProperty("fields", ex.getAllErrors()
                .stream()
                .filter(objectError -> objectError instanceof FieldError)
                .map(objectError -> (FieldError) objectError)
                .map(fieldError -> Map.of("field", fieldError.getField(), "error", StringUtils.firstNonBlank(fieldError.getDefaultMessage(), fieldError.getCode(), "未知错误")))
                .collect(Collectors.toList()));

        problemDetails.setDetail(ex.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(StringUtils::isNotBlank)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.joining(";")));
        return ResponseEntity.of(problemDetails).build();
    }


    private ProblemDetail createProblemDetails(ProcessError error) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle(error.getMessage());
        problemDetail.setType(URI.create(error.getDomain() + "/" + error.getError()));
        problemDetail.setProperty("domain", error.getDomain());
        problemDetail.setProperty("error", error.getError());
        return problemDetail;
    }

}
