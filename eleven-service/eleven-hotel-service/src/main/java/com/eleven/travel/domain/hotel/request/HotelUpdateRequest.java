package com.eleven.travel.domain.hotel.request;

import com.eleven.travel.domain.hotel.vo.AddressVo;
import com.eleven.travel.domain.hotel.vo.CheckPolicyVo;
import com.eleven.travel.domain.hotel.vo.HotelBasicVo;
import com.eleven.travel.domain.hotel.vo.PositionVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public final class HotelUpdateRequest {

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
