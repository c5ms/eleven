package com.eleven.hotel.api.interfaces.values;

import com.eleven.hotel.api.interfaces.constants.HotelConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Schema(name = "CheckPolicy")
@Accessors(chain = true)
public class CheckPolicyVo {

    @Pattern(regexp = HotelConstants.REGEXP_HH_MM)
    @Schema(example = "13:00", format = HotelConstants.FORMAT_HH_MM)
    private String checkInTime;

    @Pattern(regexp = HotelConstants.REGEXP_HH_MM)
    @Schema(example = "11:00", format = HotelConstants.FORMAT_HH_MM)
    private String checkOutTime;
}
