package com.eleven.hotel.api.interfaces.values;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Schema(name = "CheckPolicy")
@Accessors(chain = true)
public class CheckPolicyVo {

    public static final String REGEXP_HH_MM = "\\d{2}:\\d{2}";
    public static final String FORMAT_HH_MM = "HH:mm";

    @Pattern(regexp = REGEXP_HH_MM)
    @Schema(example = "13:00", format = FORMAT_HH_MM)
    private String checkInTime;

    @Pattern(regexp = REGEXP_HH_MM)
    @Schema(example = "11:00", format = FORMAT_HH_MM)
    private String checkOutTime;
}
