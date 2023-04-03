package com.demcia.eleven.core.exception;

/**
 * 表示未认证
 */
public class UnauthorizedException extends ElevenRuntimeException {

    public static UnauthorizedException of() {
        return new UnauthorizedException();
    }


}
