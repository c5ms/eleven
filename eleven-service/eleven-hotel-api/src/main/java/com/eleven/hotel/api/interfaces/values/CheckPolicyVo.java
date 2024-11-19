package com.eleven.hotel.api.interfaces.values;

import com.eleven.hotel.api.interfaces.constants.HotelInterfaceConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Schema(name = "CheckPolicy")
@Accessors(chain = true)
public class CheckPolicyVo {

    @Pattern(regexp = HotelInterfaceConstants.REGEXP_HH_MM)
    @Schema(example = "13:00", format = HotelInterfaceConstants.FORMAT_HH_MM)
    private String checkInTime;

    @Pattern(regexp = HotelInterfaceConstants.REGEXP_HH_MM)
    @Schema(example = "11:00", format = HotelInterfaceConstants.FORMAT_HH_MM)
    private String checkOutTime;
}
