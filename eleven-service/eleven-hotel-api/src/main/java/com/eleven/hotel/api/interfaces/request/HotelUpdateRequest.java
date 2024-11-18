package com.eleven.hotel.api.interfaces.request;

import com.eleven.hotel.api.interfaces.vo.AddressVo;
import com.eleven.hotel.api.interfaces.vo.CheckPolicyVo;
import com.eleven.hotel.api.interfaces.vo.HotelBasicVo;
import com.eleven.hotel.api.interfaces.vo.PositionVo;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.time.YearMonth;

@Getter
@Setter
@Hidden
public class HotelUpdateRequest {
    private HotelBasicVo hotelBasic;
    private AddressVo address;
    private PositionVo position;
    private CheckPolicyVo checkPolicy;
}
