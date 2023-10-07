package com.eleven.upms.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 系统用户状态
 */
@Getter
@RequiredArgsConstructor
public enum UserState {
    NORMAL("正常"),
    DISABLED("禁用"),
    READONLY("只读");

    private final String label;
}
