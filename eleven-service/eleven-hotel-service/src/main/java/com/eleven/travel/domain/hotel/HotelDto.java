package com.eleven.travel.domain.hotel;

import com.eleven.travel.domain.hotel.vo.AddressVo;
import com.eleven.travel.domain.hotel.vo.CheckPolicyVo;
import com.eleven.travel.domain.hotel.vo.HotelBasicVo;
import com.eleven.travel.domain.hotel.vo.PositionVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Schema(name = "Hotel")
@Accessors(chain = true)
public final class HotelDto {

    private Long hotelId;

    private Boolean active;

    @NotNull
    @JsonUnwrapped
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
