package com.motiveschina.hotel.features.hotel;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.motiveschina.hotel.features.hotel.vo.AddressVo;
import com.motiveschina.hotel.features.hotel.vo.CheckPolicyVo;
import com.motiveschina.hotel.features.hotel.vo.HotelBasicVo;
import com.motiveschina.hotel.features.hotel.vo.PositionVo;
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
