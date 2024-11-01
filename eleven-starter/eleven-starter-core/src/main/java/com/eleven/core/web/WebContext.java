package com.eleven.core.web;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.server.ResponseStatusException;

@UtilityClass
public class WebContext {

    public static ResponseStatusException notFoundException() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public static AccessDeniedException accessDeniedException() {
        return new AccessDeniedException("Access denied");
    }

}
