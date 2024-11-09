package com.eleven.booking.application.configure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(BookingApplicationProperties.class)
public class BookingApplicationConfiguration {

}
