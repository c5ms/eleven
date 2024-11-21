package com.eleven.hotel.api.interfaces.model.hotel;

import com.eleven.hotel.api.interfaces.values.AddressVo;
import com.eleven.hotel.api.interfaces.values.CheckPolicyVo;
import com.eleven.hotel.api.interfaces.values.HotelBasicVo;
import com.eleven.hotel.api.interfaces.values.PositionVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
public final class HotelCreateRequest {

    @NotNull
    @JsonUnwrapped(prefix = "hotel_")
    private HotelBasicVo basic;

    @NotNull
    @JsonUnwrapped(prefix = "address_")
    private AddressVo address;

    @NotNull
    @JsonUnwrapped(prefix = "position_")
    private PositionVo position;

    @NotNull
    @JsonUnwrapped(prefix = "checkPolicy_")
    private CheckPolicyVo checkPolicy;

}
