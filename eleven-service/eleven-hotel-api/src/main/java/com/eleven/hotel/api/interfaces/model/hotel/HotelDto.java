package com.eleven.hotel.api.interfaces.model.hotel;

import com.eleven.hotel.api.interfaces.values.AddressVo;
import com.eleven.hotel.api.interfaces.values.CheckPolicyVo;
import com.eleven.hotel.api.interfaces.values.HotelBasicVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Schema(name = "Hotel")
@Accessors(chain = true)
public class HotelDto {

    private Long hotelId;
    private Boolean active;
    private HotelBasicVo hotelBasic;
    private AddressVo address;
    private AddressVo.PositionVo position;
    private CheckPolicyVo checkPolicy;
}
