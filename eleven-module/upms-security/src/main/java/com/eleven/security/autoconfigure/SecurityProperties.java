package com.eleven.security.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "eleven.security")
public class SecurityProperties {

    /**
     * 认证模式
     */
    private AuthMode authMode;

    enum AuthMode {
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

}
