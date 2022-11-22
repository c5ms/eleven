package com.demcia.eleven.core.rest.support;

import com.demcia.eleven.core.exception.DataNotFoundException;
import com.demcia.eleven.core.exception.PermissionDeadException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestApiAdvice {

    // 资源不存在 204
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(DataNotFoundException.class)
    public void customException(DataNotFoundException e) {
//        return new RestApiFailureResult()
//                .setMessage(StringUtils.defaultIfBlank(e.getMessage(),"资源不存在"))
//                .setError("resource not found");
    }


    // 权限不足 - 403
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(PermissionDeadException.class)
    public void customException(PermissionDeadException e) {
//        return new RestApiFailureResult()
//                .setMessage(StringUtils.defaultIfBlank(e.getMessage(),"权限不足"))
//                .setError("permission dead");
    }

}
