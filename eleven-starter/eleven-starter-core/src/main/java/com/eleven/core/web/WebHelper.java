package com.eleven.core.web;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@UtilityClass
public class WebHelper {

    public static ResponseStatusException createNotFoundEx() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public static void throwNotFoundEx() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
