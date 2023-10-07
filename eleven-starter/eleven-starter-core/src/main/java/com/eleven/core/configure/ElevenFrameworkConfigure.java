package com.eleven.core.configure;

import com.eleven.core.domain.BeanConvertor;
import com.eleven.core.domain.support.ModelMapperBeanConvertor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@EnableCaching
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class ElevenFrameworkConfigure {

    @Bean
    BeanConvertor beanConvertor(ModelMapper modelMapper) {
        return new ModelMapperBeanConvertor(modelMapper);
    }


    @Bean
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setFieldMatchingEnabled(true)
                // TODO STRICT or STANDARD ?
                .setMatchingStrategy(MatchingStrategies.STRICT)
        ;
        return modelMapper;
    }
}
