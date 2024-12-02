package com.eleven.hotel.interfaces.configure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "eleven.hotel.interfaces")
public class HotelInterfacesProperties {
}
