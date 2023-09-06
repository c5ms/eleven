package com.eleven.core.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "eleven.rest")
public class ElevenRestProperties {


}
