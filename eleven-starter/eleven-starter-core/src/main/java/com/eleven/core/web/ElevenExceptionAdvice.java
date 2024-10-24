package com.eleven.core.web;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.eleven.core.command.CommandHandleException;
import com.eleven.core.domain.DomainError;
import com.eleven.core.domain.DomainErrors;
import com.eleven.core.domain.DomainException;
import com.eleven.core.domain.NoRequiredEntityException;
import com.eleven.core.web.problem.Problem;
import com.eleven.core.web.problem.ValidationProblem;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@Slf4j
@ControllerAdvice
public class ElevenExceptionAdvice {




    @ResponseBody
    @ApiResponse(description = "Unprocessable Entity", responseCode = "422")
    @ApiResponse(description = "Bad Request", responseCode = "400")
    @ApiResponse(description = "Not Found", responseCode = "404")
    @ApiResponse(description = "Method Not Allowed", responseCode = "405")
    @ApiResponse(description = "Unsupported Media Type", responseCode = "415")
    @ApiResponse(description = "Internal Server Error", responseCode = "500")
    @ExceptionHandler({Exception.class,})
    public ResponseEntity<Problem> on(Exception e) {
        HttpStatus status;

        //400 - payload
        if (e instanceof CommandHandleException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (e instanceof NoRequiredEntityException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (e instanceof HttpMessageConversionException) {
            log.warn("message convert fail {}", ExceptionUtil.getMessage(e));
            var problem = Problem.of(DomainErrors.ERROR_REQUEST_BODY_FAILED);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
        } else if (e instanceof DomainException ex) {
            var problem = Problem.of(ex);
            log.warn(ExceptionUtil.getMessage(ex), ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
        }


        // 422
        else if (e instanceof BindException ex) {
            var problem = ValidationProblem.empty();
            ex.getAllErrors()
                .stream()
                .filter(objectError -> objectError instanceof FieldError)
                .map(objectError -> (FieldError) objectError)
                .map(fieldError -> new ValidationProblem.Field(fieldError.getField(), fieldError.getCode(), fieldError.getDefaultMessage()))
                .forEach(problem::addField);

            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(problem);
        }

        // 403
        else if (e instanceof AccessDeniedException) {
            var problem = Problem.of(DomainErrors.ERROR_ACCESS_DENIED);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problem);
        }

        //404
        else if (e instanceof NoHandlerFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (e instanceof NoResourceFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (e instanceof ResourceNofFoundException) {
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
//            log.error("internal server error", e);
        }

        return ResponseEntity.status(status).build();
    }


}
