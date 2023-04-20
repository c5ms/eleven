package com.eleven.core.service.rest.exception;

import org.springframework.http.HttpStatus;

/**
 * 用于表示数据不存在
 */
public class NotFoundException extends ClientErrorException {

    public NotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }

}
