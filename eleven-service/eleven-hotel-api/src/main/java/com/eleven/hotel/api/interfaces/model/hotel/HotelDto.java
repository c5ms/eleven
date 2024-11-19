package com.eleven.hotel.api.interfaces.model.hotel;

import com.eleven.hotel.api.interfaces.values.AddressVo;
import com.eleven.hotel.api.interfaces.values.CheckPolicyVo;
import com.eleven.hotel.api.interfaces.values.HotelBasicVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
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

    @JsonUnwrapped(prefix = "hotel_")
    private HotelBasicVo basic;

    @JsonUnwrapped(prefix = "address_")
    private AddressVo address;

    @JsonUnwrapped(prefix = "position_")
    private AddressVo.PositionVo position;

    @JsonUnwrapped(prefix = "checkPolicy_")
    private CheckPolicyVo checkPolicy;
}
