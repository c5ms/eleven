package com.eleven.upms.domain;

import com.eleven.core.exception.ElevenError;
import com.eleven.core.exception.ElevenRuntimeException;

public class UserException extends ElevenRuntimeException {

    public UserException(ElevenError error) {
        super(error);
    }

    public UserException(ElevenError error, String message) {
        super(error, message);
    }
}
