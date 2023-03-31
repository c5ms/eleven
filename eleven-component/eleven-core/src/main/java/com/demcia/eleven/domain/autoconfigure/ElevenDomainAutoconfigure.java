package com.demcia.eleven.domain.autoconfigure;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableCaching
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@PropertySource("classpath:/config/application-domain.properties")
public class ElevenDomainAutoconfigure {

}