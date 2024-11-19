package com.eleven.hotel.api.interfaces.model.hotel;

import com.eleven.hotel.api.interfaces.values.AddressVo;
import com.eleven.hotel.api.interfaces.values.CheckPolicyVo;
import com.eleven.hotel.api.interfaces.values.HotelBasicVo;
import com.eleven.hotel.api.interfaces.values.PositionVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

@Data
public class HotelCreateRequest {

    @JsonUnwrapped
    private HotelBasicVo basic;

    private AddressVo address;

    private PositionVo position;

    private CheckPolicyVo checkPolicy;

}
