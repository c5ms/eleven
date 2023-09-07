package com.eleven.security.configure;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(UpmsSecurityProperties.class)
public class UpmsSecurityConfigure {


}
