package com.eleven.core.domain.configure;

import cn.hutool.core.lang.Snowflake;
import com.eleven.core.domain.IdentityGenerator;
import com.eleven.core.domain.SnowflakeIdentityGenerator;
import com.eleven.core.domain.support.NanoIdGenerator;
import com.eleven.core.domain.support.ObjectIdGenerator;
import com.eleven.core.domain.support.RaindropGenerator;
import com.eleven.core.domain.support.UuidGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;


@EnableJdbcAuditing
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@EnableConfigurationProperties(DomainProperties.class)
@PropertySource("classpath:/config/application-domain.properties")
public class DomainAutoconfigure {

    private final DomainProperties domainProperties;


    @Bean
    public IdentityGenerator idGenerator() {
        return switch (domainProperties.getIdType()) {
            case SNOWFLAKE -> new SnowflakeIdentityGenerator(new Snowflake());
            case NANOID -> new NanoIdGenerator();
            case OBJECT -> new ObjectIdGenerator();
            case RAINDROP -> new RaindropGenerator();
            default -> new UuidGenerator();
        };
    }


}
