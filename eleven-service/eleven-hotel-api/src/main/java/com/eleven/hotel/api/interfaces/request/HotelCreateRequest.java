package com.eleven.hotel.api.interfaces.request;

import com.eleven.hotel.api.interfaces.constants.HotelConstants;
import com.eleven.hotel.api.interfaces.vo.AddressVo;
import com.eleven.hotel.api.interfaces.vo.CheckPolicyVo;
import com.eleven.hotel.api.interfaces.vo.HotelBasicVo;
import com.eleven.hotel.api.interfaces.vo.PositionVo;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.YearMonth;

@Getter
@Setter
public class HotelCreateRequest {

    private HotelBasicVo hotelBasic;
    private AddressVo address;
    private PositionVo position;
    private CheckPolicyVo checkPolicy;
}
