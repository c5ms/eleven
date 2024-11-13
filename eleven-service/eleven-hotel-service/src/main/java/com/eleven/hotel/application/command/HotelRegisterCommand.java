package com.eleven.hotel.application.command;

import com.eleven.hotel.domain.model.register.Register;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HotelRegisterCommand {
    private Register.HotelInformation hotel;
    private Register.AdminInformation admin;
}
