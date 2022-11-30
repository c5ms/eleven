package com.demcia.eleven.core.rest.support;

import com.demcia.eleven.core.rest.RestfulFailure;
import io.swagger.v3.oas.annotations.Hidden;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Hidden
@Controller
public class RestfulErrorController implements ErrorController {

    @ResponseBody
    @RequestMapping("/error")
    public ResponseEntity<RestfulFailure> handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String message = (String) request.getAttribute("javax.servlet.error.message");
        if (null != statusCode) {
            HttpStatus status = getStatus(request);
            if (status == HttpStatus.NO_CONTENT) {
                return new ResponseEntity<>(status);
            }
            RestfulFailure failure = new RestfulFailure()
                    .setError(status.getReasonPhrase())
                    .setMessage(StringUtils.defaultIfBlank(message, null));
            return ResponseEntity.status(status)
                    .body(failure);
        }

        return ResponseEntity.status(500).body(null);
    }


    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}