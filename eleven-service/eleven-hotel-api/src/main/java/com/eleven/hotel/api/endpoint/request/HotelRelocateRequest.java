package com.eleven.hotel.api.endpoint.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelRelocateRequest {

    private String province;
    private String city;
    private String district;
    private String street;
    private String address;
    private Double lat;
    private Double lng;

}
