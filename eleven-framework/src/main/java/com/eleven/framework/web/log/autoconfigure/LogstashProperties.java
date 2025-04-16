package com.eleven.framework.web.log.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "eleven.logs.logstash")
public class LogstashProperties {
    private boolean enabled;
    private String url = "localhost:5000";
    private String trustStoreLocation;
    private String trustStorePassword;
}
