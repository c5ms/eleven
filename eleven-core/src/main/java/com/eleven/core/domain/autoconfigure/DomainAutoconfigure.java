package com.eleven.core.domain.autoconfigure;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;


@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@PropertySource("classpath:/config/application-domain.properties")
public class DomainAutoconfigure {

    @Bean
    public DefaultMapperFactory mapperFactory() {
        return new DefaultMapperFactory.Builder()
                .useBuiltinConverters(true)
                .useAutoMapping(true)
                .mapNulls(true)
                .build()
                ;
    }

    @Bean
    public MapperFacade mapperFacade(DefaultMapperFactory mapperFactory) {
        return mapperFactory.getMapperFacade();
    }

}
