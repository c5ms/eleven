package com.eleven.core.web;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@UtilityClass
public class WebContext {

    public static ResponseStatusException notFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public static void throwNotFound() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
