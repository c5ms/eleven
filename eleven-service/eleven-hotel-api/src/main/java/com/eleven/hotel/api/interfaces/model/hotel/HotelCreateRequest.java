package com.eleven.hotel.api.interfaces.model.hotel;

import com.eleven.hotel.api.interfaces.values.AddressVo;
import com.eleven.hotel.api.interfaces.values.CheckPolicyVo;
import com.eleven.hotel.api.interfaces.values.HotelBasicVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

@Data
public class HotelCreateRequest {

    @JsonUnwrapped(prefix = "hotel_")
    private HotelBasicVo basic;

    @JsonUnwrapped(prefix = "address_")
    private AddressVo address;

    @JsonUnwrapped(prefix = "position_")
    private AddressVo.PositionVo position;

    @JsonUnwrapped(prefix = "checkPolicy_")
    private CheckPolicyVo checkPolicy;

}
