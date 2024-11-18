package com.eleven.hotel.api.interfaces.dto;

import com.eleven.hotel.api.interfaces.vo.AddressVo;
import com.eleven.hotel.api.interfaces.vo.CheckPolicyVo;
import com.eleven.hotel.api.interfaces.vo.HotelBasicVo;
import com.eleven.hotel.api.interfaces.vo.PositionVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalTime;

@Getter
@Setter
@Schema(name = "Hotel")
@Accessors(chain = true)
public class HotelDto {

    private Long hotelId;
    private Boolean active;
    private HotelBasicVo hotelBasic;
    private AddressVo address;
    private PositionVo position;
    private CheckPolicyVo checkPolicy;
}
