package com.eleven.hotel.api.interfaces.model.hotel;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Hidden
public class HotelQueryRequest {
    private String hotelName;
}
