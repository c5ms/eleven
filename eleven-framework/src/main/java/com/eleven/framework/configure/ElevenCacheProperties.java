package com.eleven.framework.configure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter
@Setter
@ConfigurationProperties(prefix = "eleven.cache")
final class ElevenCacheProperties {
    private Duration duration = Duration.parse("PT8H");
}
