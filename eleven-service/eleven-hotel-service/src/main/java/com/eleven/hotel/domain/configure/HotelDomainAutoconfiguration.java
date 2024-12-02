package com.eleven.hotel.domain.configure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(HotelDomainProperties.class)
public class HotelDomainAutoconfiguration {

}
