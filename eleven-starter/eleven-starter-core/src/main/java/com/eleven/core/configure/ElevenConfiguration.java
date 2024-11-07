package com.eleven.core.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration(proxyBeanMethods = false)
@PropertySource("classpath:/config/application-core.properties")
public class ElevenConfiguration {

}
