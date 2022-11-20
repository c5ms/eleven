package com.eleven.hotel.api.application.model.values;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class PositionDto {
    private String province;
    private String city;
    private String district;
    private String street;
    private String address;
    private Double lat;
    private Double lng;
}
