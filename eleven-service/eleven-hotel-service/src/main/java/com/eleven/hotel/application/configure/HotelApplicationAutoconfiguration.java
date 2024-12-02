package com.eleven.hotel.application.configure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(HotelApplicationProperties.class)
public class HotelApplicationAutoconfiguration {

}
