package com.eleven.framework.configure;

import cn.hutool.core.lang.Snowflake;
import com.eleven.framework.domain.DomainAuditorAware;
import com.eleven.framework.domain.IdentityGenerator;
import com.eleven.framework.domain.support.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@ComponentScan("com.eleven.framework.domain")
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
