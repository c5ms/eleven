package com.eleven.hotel.api.interfaces.model.hotel;

import com.eleven.hotel.api.interfaces.values.AddressVo;
import com.eleven.hotel.api.interfaces.values.CheckPolicyVo;
import com.eleven.hotel.api.interfaces.values.HotelBasicVo;
import com.eleven.hotel.api.interfaces.values.PositionVo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelUpdateRequest {
    private HotelBasicVo basic;
    private AddressVo address;
    private PositionVo position;
    private CheckPolicyVo checkPolicy;
}
