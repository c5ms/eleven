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

}
