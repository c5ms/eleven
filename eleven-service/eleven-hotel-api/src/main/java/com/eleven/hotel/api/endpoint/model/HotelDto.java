package com.eleven.hotel.api.endpoint.model;

import com.eleven.hotel.api.domain.model.SaleState;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalTime;

@Getter
@Setter
@Accessors(chain = true)
public class HotelDto {

    private Integer hotelId;

    private String name;
    private String description;
    private String headPicUrl;
    private Integer totalRooms;

    private LocalTime checkIn;
    private LocalTime checkOut;
    private SaleState state;
    private String tel;
    private String email;

    private String province;
    private String city;
    private String district;
    private String street;
    private String address;
    private Double lat;
    private Double lng;

}
