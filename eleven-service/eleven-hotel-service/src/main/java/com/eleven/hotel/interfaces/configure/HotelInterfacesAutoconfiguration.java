package com.eleven.hotel.interfaces.configure;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(HotelInterfacesProperties.class)
public class HotelInterfacesAutoconfiguration implements WebMvcConfigurer {


}
