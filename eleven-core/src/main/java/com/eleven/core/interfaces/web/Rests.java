package com.eleven.core.interfaces.web;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@UtilityClass
public class Rests {

    public static ResponseStatusException throwFor(HttpStatus status) {
        return new ResponseStatusException(status);
    }

    public static ResponseStatusException throw404() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public static ResponseStatusException throw401() {
        return new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    public static ResponseStatusException throw403() {
        return new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

}
