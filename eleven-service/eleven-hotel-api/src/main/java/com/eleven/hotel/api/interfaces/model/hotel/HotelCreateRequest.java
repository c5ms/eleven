package com.eleven.hotel.api.interfaces.model.hotel;

import com.eleven.hotel.api.interfaces.values.AddressVo;
import com.eleven.hotel.api.interfaces.values.CheckPolicyVo;
import com.eleven.hotel.api.interfaces.values.HotelBasicVo;
import lombok.Data;

@Data
public class HotelCreateRequest {

    private HotelBasicVo basic;
    private AddressVo address;
    private AddressVo.PositionVo position;
    private CheckPolicyVo checkPolicy;

}
