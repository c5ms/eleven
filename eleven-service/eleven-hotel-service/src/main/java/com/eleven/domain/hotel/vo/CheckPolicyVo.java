package com.eleven.domain.hotel.vo;

import com.eleven.common.layer.Vo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Schema(name = "CheckPolicy")
public class CheckPolicyVo extends Vo {

    public static final String REGEXP_HH_MM = "\\d{2}:\\d{2}";
    public static final String FORMAT_HH_MM = "HH:mm";

    @NotEmpty
    @Pattern(regexp = REGEXP_HH_MM)
    @Schema(example = "13:00", format = FORMAT_HH_MM)
    private String checkInTime;

    @NotEmpty
    @Pattern(regexp = REGEXP_HH_MM)
    @Schema(example = "11:00", format = FORMAT_HH_MM)
    private String checkOutTime;
}
