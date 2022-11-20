package com.eleven.hotel.application.command;

import com.eleven.hotel.domain.values.Contact;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HotelRegisterCommand {

    @NotBlank
    private String hotelName;

    @NotBlank
    private String hotelAddress;

    @NotBlank
    private String adminName;

    @NotBlank
    private Contact adminContact;

}
