package com.eleven.core.cache.configure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter
@Setter
@ConfigurationProperties(prefix = "eleven.cache")
public class ElevenCacheProperties {
    private Duration duration = Duration.parse("PT8H");
    private SerializerType serializer = SerializerType.JACKSON;
}
