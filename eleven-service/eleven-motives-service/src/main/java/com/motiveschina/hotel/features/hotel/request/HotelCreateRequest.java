package com.motiveschina.hotel.features.hotel.request;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.motiveschina.hotel.features.hotel.vo.AddressVo;
import com.motiveschina.hotel.features.hotel.vo.CheckPolicyVo;
import com.motiveschina.hotel.features.hotel.vo.HotelBasicVo;
import com.motiveschina.hotel.features.hotel.vo.PositionVo;
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
