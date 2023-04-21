package com.eleven.upms.domain;

import com.eleven.core.code.ElevenCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserError implements ElevenCode {
    USER_NAME_REPEAT("upms:user_name_repeat", "用户名重复");

    final String code;
    final String message;

}
