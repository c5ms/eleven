package com.eleven.hotel.api.interfaces.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelQueryRequest {

    @Schema(description = "hotel name")
    private String hotelName;

}
