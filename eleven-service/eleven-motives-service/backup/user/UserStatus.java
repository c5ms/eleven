package com.motiveschina.hotel.features.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 系统用户状态
 */
@Getter
@RequiredArgsConstructor
public enum UserStatus {

    NORMAL("正常"),
    DISABLED("禁用"),
    READONLY("只读");

    private final String label;
}
