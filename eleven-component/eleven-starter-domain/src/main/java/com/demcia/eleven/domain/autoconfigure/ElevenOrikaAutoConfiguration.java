package com.demcia.eleven.domain.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration(proxyBeanMethods = false)
public class ElevenOrikaAutoConfiguration {

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
