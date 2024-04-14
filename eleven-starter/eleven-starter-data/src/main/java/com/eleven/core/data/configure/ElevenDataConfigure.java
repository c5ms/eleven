package com.eleven.core.data.configure;

import cn.hutool.core.lang.Snowflake;
import com.eleven.core.data.IdentityGenerator;
import com.eleven.core.data.support.NanoIdGenerator;
import com.eleven.core.data.support.ObjectIdGenerator;
import com.eleven.core.data.support.RaindropGenerator;
import com.eleven.core.data.support.SnowflakeIdentityGenerator;
import com.eleven.core.data.support.UuidGenerator;
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
@EnableConfigurationProperties(ElevenDataProperties.class)
@PropertySource("classpath:/config/application-data.properties")
public class ElevenDataConfigure {

    private final ElevenDataProperties elevenDataProperties;


    @Bean
    public IdentityGenerator idGenerator() {
        return switch (elevenDataProperties.getIdType()) {
            case SNOWFLAKE -> new SnowflakeIdentityGenerator(new Snowflake());
            case NANOID -> new NanoIdGenerator();
            case OBJECT -> new ObjectIdGenerator();
            case RAINDROP -> new RaindropGenerator();
            default -> new UuidGenerator();
        };
    }


}
