package com.eleven.hotel.api.application.view;

import com.eleven.hotel.api.domain.model.SaleState;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalTime;

@Getter
@Setter
@Accessors(chain = true)
public class HotelDto {

    private String id;
    private String name;

    private String description;
    private String headPicUrl;
    private Integer roomNumber;

    private String tel;
    private String email;

    private LocalTime checkIn;
    private LocalTime checkOut;
    private SaleState state;

    private String province;
    private String city;
    private String district;
    private String street;
    private String address;
    private Double lat;
    private Double lng;


}
