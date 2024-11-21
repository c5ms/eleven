package com.eleven.hotel.api.interfaces.model.hotel;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
public final class  HotelQueryRequest {
    private String hotelName;
}
