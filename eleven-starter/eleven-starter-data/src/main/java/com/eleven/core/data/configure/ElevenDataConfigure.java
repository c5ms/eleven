package com.eleven.core.data.configure;

import cn.hutool.core.lang.Snowflake;
import com.eleven.core.data.support.*;
import com.eleven.core.domain.IdentityGenerator;
import com.eleven.core.domain.support.*;
import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

import java.util.ArrayList;
import java.util.List;


@EnableJdbcAuditing
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@EnableConfigurationProperties(ElevenDataProperties.class)
@PropertySource("classpath:/config/application-data.properties")
public class ElevenDataConfigure extends AbstractJdbcConfiguration {

    private final ElevenDataProperties elevenDataProperties;
    private final ModelMapper modelMapper;

    @Override
    @Nonnull
    protected List<?> userConverters() {
        return List.of(
            new ListValueWriteConverter(),
            new ListValueReadConverter()
        );
    }

    @PostConstruct
    void init() {
        modelMapper.addConverter(new Converter<ListValue<?>, List<?>>() {
            @Override
            public List<?> convert(MappingContext<ListValue<?>, List<?>> context) {
                return new ArrayList<>(context.getSource().getValues());
            }
        });
    }

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
