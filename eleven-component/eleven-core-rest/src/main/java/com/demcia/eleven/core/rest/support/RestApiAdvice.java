package com.demcia.eleven.core.rest.support;

import com.demcia.eleven.core.exception.DataNotFoundException;
import com.demcia.eleven.core.exception.PermissionDeadException;
import com.demcia.eleven.core.rest.constants.RestApiConstants;
import com.demcia.eleven.core.rest.support.result.RestApiFailureResult;
import org.apache.commons.lang3.StringUtils;
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
//                .setError(RestApiConstants.ERROR_RESOURCE_NOT_EXIST);
    }


    // 权限不足 - 403
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(PermissionDeadException.class)
    public RestApiFailureResult customException(PermissionDeadException e) {
        return new RestApiFailureResult()
                .setMessage(StringUtils.defaultIfBlank(e.getMessage(),"权限不足"))
                .setError(RestApiConstants.ERROR_PERMISSION_DEAD);
    }

}
