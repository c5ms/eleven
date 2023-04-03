package com.demcia.eleven.core.generate.autoconfigure;

import cn.hutool.core.util.IdUtil;
import com.demcia.eleven.core.generate.IdentityGenerator;
import com.demcia.eleven.core.generate.support.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(IdentityProperties.class)
public class IdentityAutoconfigure {

    private final IdentityProperties dataProperties;


    @Bean
    public IdentityGenerator idGenerator() {
        return switch (dataProperties.getIdType()) {
            case SNOWFLAKE ->
                    new SnowflakeIdentityGenerator(IdUtil.getSnowflake(dataProperties.getSnowflake().getDatacenterId()));
            case NANOID -> new NanoIdGenerator();
            case OBJECT -> new ObjectIdGenerator();
            case RAINDROP -> new RaindropGenerator(dataProperties.getSnowflake().getDatacenterId());
            default -> new UuidGenerator();
        };
    }


}
