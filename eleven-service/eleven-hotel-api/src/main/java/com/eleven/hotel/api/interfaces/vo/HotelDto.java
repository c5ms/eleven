package com.eleven.hotel.api.interfaces.vo;

import com.eleven.hotel.api.domain.values.SaleState;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalTime;

@Getter
@Setter
@Accessors(chain = true)
public class HotelDto {

    private Long hotelId;
    private Boolean active;

    private String name;
    private String description;
    private Integer totalRoomQuantity;
    private String phone;
    private String email;

    private LocalTime checkIn;
    private LocalTime checkOut;

    private String country;
    private String province;
    private String city;
    private String location;
    private String address;

    private Double latitude;
    private Double longitude;

}
