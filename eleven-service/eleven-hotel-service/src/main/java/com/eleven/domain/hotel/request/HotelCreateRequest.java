package com.eleven.domain.hotel.request;

import com.eleven.domain.hotel.vo.AddressVo;
import com.eleven.domain.hotel.vo.CheckPolicyVo;
import com.eleven.domain.hotel.vo.HotelBasicVo;
import com.eleven.domain.hotel.vo.PositionVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
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
