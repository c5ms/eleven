package com.eleven.hotel.application.command;

import com.eleven.hotel.domain.model.hotel.Register;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HotelRegisterCommand {
    @NotBlank
    private Register.HotelInformation hotel;

    @NotBlank
    private Register.AdminInformation admin;
}
