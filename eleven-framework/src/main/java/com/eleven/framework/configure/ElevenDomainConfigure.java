package com.eleven.framework.configure;

import cn.hutool.core.lang.Snowflake;
import com.eleven.framework.data.DomainAuditorAware;
import com.eleven.framework.data.IdentityGenerator;
import com.eleven.framework.data.support.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
@EnableConfigurationProperties(ElevenDomainProperties.class)
final class ElevenDomainConfigure {

    private final ElevenDomainProperties elevenDomainProperties;

    @Bean
    IdentityGenerator idGenerator() {
        return switch (elevenDomainProperties.getIdType()) {
            case SNOWFLAKE -> new SnowflakeIdentityGenerator(new Snowflake());
            case NANOID -> new NanoIdGenerator();
            case OBJECT -> new ObjectIdGenerator();
            case RAINDROP -> new RaindropGenerator();
            default -> new UuidGenerator();
        };
    }

    @Bean
    DomainAuditorAware auditorAware() {
        return new DomainAuditorAware();
    }

}
