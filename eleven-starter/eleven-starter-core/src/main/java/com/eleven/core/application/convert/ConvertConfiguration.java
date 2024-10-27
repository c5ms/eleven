package com.eleven.core.application.convert;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class ConvertConfiguration {

    @Bean
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
            .setAmbiguityIgnored(true)
            .setFieldMatchingEnabled(true)
            .setImplicitMappingEnabled(true)
            .setMatchingStrategy(MatchingStrategies.STANDARD)
        ;
        return modelMapper;
    }
}
