package com.eleven.core.data.configure;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "eleven.data")
public class ElevenDataProperties {

    private IdGeneratorType idType = IdGeneratorType.RAINDROP;

    private Integer serialCacheSize = 5;

    enum IdGeneratorType {
        UUID,
        SNOWFLAKE,
        RAINDROP,
        NANOID,
        OBJECT
    }

}
