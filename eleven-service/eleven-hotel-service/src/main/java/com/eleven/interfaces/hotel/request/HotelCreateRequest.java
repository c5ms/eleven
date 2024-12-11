package com.eleven.interfaces.hotel.request;

import com.eleven.interfaces.hotel.vo.AddressVo;
import com.eleven.interfaces.hotel.vo.CheckPolicyVo;
import com.eleven.interfaces.hotel.vo.HotelBasicVo;
import com.eleven.interfaces.hotel.vo.PositionVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public final class HotelCreateRequest {

    @NotNull
    @Valid
    @JsonUnwrapped(prefix = "hotel")
    private HotelBasicVo basic;

    @NotNull
    @Valid
    @JsonUnwrapped(prefix = "address")
    private AddressVo address;

    @NotNull
    @Valid
    @JsonUnwrapped(prefix = "position")
    private PositionVo position;

    @NotNull
    @Valid
    @JsonUnwrapped(prefix = "checkPolicy")
    private CheckPolicyVo checkPolicy;

}
