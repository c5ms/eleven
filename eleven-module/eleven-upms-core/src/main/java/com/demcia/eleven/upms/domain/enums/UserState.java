package com.demcia.eleven.upms.domain.enums;

import com.demcia.eleven.core.enums.ElevenEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 系统用户状态
 */
@Getter
@RequiredArgsConstructor
public enum UserState implements ElevenEnum {
    NORMAL("正常"),
    DISABLED("禁用"),
    READONLY("只读");

    private final String label;
}
