package com.eleven.hotel.application.configure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(HotelApplicationProperties.class)
public class HotelApplicationAutoconfiguration {

}
