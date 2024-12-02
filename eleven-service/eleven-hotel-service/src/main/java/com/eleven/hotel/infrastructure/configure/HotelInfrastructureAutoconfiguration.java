package com.eleven.hotel.infrastructure.configure;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(HotelInfrastructureProperties.class)
public class HotelInfrastructureAutoconfiguration {


}
