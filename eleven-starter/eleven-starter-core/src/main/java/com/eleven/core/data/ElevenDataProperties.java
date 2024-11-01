package com.eleven.core.data;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "eleven.data")
public class ElevenDataProperties {

    private IdGeneratorType idType = IdGeneratorType.RAINDROP;

    enum IdGeneratorType {
        UUID,
        SNOWFLAKE,
        RAINDROP,
        NANOID,
        OBJECT
    }

}
