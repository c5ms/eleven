package com.eleven.core.web;

import com.eleven.core.constants.ElevenConstants;
import com.eleven.core.exception.DataNotFoundException;
import com.eleven.core.exception.ProcessError;
import com.eleven.core.exception.ProcessRuntimeException;
import com.eleven.core.web.problem.Problem;
import com.eleven.core.web.problem.ValidationProblem;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


@Slf4j
@ControllerAdvice
public class ElevenExceptionAdvice {

    @ApiResponse(description = "Unprocessable Entity", responseCode = "422")
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    @ExceptionHandler({BindException.class,})
    public ResponseEntity<ValidationProblem> on(BindException ex) {
        var problem = ValidationProblem.empty();
        ex.getAllErrors()
                .stream()
                .filter(objectError -> objectError instanceof FieldError)
                .map(objectError -> (FieldError) objectError)
                .map(fieldError -> new ValidationProblem.Field(fieldError.getField(), fieldError.getCode(), fieldError.getDefaultMessage()))
                .forEach(problem::addField);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(problem);
    }

    @ResponseBody
    @ApiResponse(description = "Bad Request", responseCode = "400")
    @ExceptionHandler({Exception.class,})
    public ResponseEntity<Problem> on(Exception e) {
        HttpStatus status;

        //400 - payload
        if (e instanceof HttpMessageConversionException) {
            var problem = Problem.of(ElevenConstants.ERROR_JSON_PARSING);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(problem);
        }

        // 400 -  process
        else if (e instanceof ProcessRuntimeException) {
            var problem = Problem.of((ProcessError) e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(problem);

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

        return ResponseEntity.status(status).build();
    }


}
