package com.eleven.framework.web;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@UtilityClass
public class Rests {

    public static ResponseStatusException createEx(HttpStatus status) {
        return new ResponseStatusException(status);
    }

    public static ResponseStatusException create404() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public static ResponseStatusException create401() {
        return new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    public static ResponseStatusException create403() {
        return new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

}
