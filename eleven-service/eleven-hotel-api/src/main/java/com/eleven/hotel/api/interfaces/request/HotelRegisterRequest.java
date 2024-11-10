package com.eleven.hotel.api.interfaces.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelRegisterRequest {

    @NotBlank
    private String hotelName;

    @NotBlank
    private String hotelAddress;

    @NotBlank
    private String adminName;

    @NotBlank
    private String adminEmail;

    @NotBlank
    private String adminTel;
}
