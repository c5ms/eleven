package com.eleven.hotel.api.endpoint.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class HotelUpdateRequest {

    private String name;
    private String description;
    private String headPicUrl;
    private Integer roomNumber;

    private String tel;
    private String email;

    private LocalTime checkIn;
    private LocalTime checkOut;

    private String province;
    private String city;
    private String district;
    private String street;
    private String address;
    private Double lat;
    private Double lng;

}
