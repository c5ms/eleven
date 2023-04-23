package com.eleven.upms.domain.configure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "eleven.upms")
public class UpmsProperties {
    private String defaultPassword = "123456";
}
