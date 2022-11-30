package com.demcia.eleven.upms.core.code;

import com.demcia.eleven.core.codes.ElevenCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrors implements ElevenCode {
    USER_NAME_REPEAT("UPMS:USER_NAME_REPEAT", "用户名重复");

    final String code;
    final String message;

}
