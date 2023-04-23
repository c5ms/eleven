package com.eleven.core.generate.autoconfigure;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "eleven.domain.identity")
public class IdentityProperties {

    private IdGeneratorType idType = IdGeneratorType.RAINDROP;

    enum IdGeneratorType {
        UUID,
        SNOWFLAKE,
        RAINDROP,
        NANOID,
        OBJECT
    }

}
