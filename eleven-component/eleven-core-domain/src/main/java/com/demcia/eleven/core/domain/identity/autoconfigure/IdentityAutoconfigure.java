package com.demcia.eleven.core.domain.identity.autoconfigure;

import com.demcia.eleven.core.domain.identity.IdGenerator;
import com.demcia.eleven.core.domain.identity.provider.RaindropIdGenerator;
import com.demcia.eleven.core.domain.identity.provider.SnowflakeIdGenerator;
import com.demcia.eleven.core.domain.identity.provider.SnowflakeIdWorker;
import com.demcia.eleven.core.domain.identity.provider.UuidGenerator;
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
    public IdGenerator idGenerator() {
        switch (dataProperties.getIdType()) {
            case SNOWFLAKE:
                return new SnowflakeIdGenerator(new SnowflakeIdWorker(dataProperties.getSnowflake().getWordId(), dataProperties.getSnowflake().getDatacenterId()));
            case RAINDROP:
                return new RaindropIdGenerator(dataProperties.getRaindrop().getPrefix());
            default:
                return new UuidGenerator();
        }
    }


}
