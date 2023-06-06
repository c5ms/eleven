package com.eleven.upms.domain;

import com.eleven.core.exception.ElevenError;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserError implements ElevenError {
    USER_NAME_REPEAT("upms:user_name_repeat", "用户名重复");

    final String error;
    final String message;

    @Override
    public UserException exception() {
        return new UserException(this);
    }

}
