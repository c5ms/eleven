package com.eleven.hotel.api.application.model;

import com.eleven.hotel.api.domain.model.RegisterState;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class RegisterDto {
    private Long registerID;
    private String hotelName;
    private String hotelAddress;
    private String adminName;
    private String adminEmail;
    private String adminTel;
    private RegisterState state;
}
