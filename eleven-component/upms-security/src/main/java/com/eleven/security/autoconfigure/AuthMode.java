package com.eleven.security.autoconfigure;

public enum AuthMode {
    /**
     * 无需权限认证
     */
    none,
    /**
     * 远程认证策略
     */
    remote,
    /**
     * 本地认证策略
     */
    local
}
