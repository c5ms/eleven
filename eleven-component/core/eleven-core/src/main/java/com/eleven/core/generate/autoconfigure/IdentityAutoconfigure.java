package com.eleven.core.generate.autoconfigure;

import cn.hutool.core.lang.Snowflake;
import com.eleven.core.generate.IdentityGenerator;
import com.eleven.core.generate.support.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(IdentityProperties.class)
public class IdentityAutoconfigure {

    private final IdentityProperties identityProperties;


    @Bean
    public IdentityGenerator idGenerator() {
        return switch (identityProperties.getIdType()) {
            case SNOWFLAKE -> new SnowflakeIdentityGenerator(new Snowflake());
            case NANOID -> new NanoIdGenerator();
            case OBJECT -> new ObjectIdGenerator();
            case RAINDROP -> new RaindropGenerator();
            default -> new UuidGenerator();
        };
    }


}
