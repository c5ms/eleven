package com.eleven.upms.domain.configure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(UpmsProperties.class)
public class UpmsConfiguration {

}
