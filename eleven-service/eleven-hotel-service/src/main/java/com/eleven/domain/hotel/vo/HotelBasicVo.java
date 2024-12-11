package com.eleven.domain.hotel.vo;

import com.eleven.common.layer.Vo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.YearMonth;

@Getter
@Setter
@Accessors(chain = true)
@Schema(name = "HotelBasic")
public class HotelBasicVo extends Vo {

    @NotEmpty
    @Schema(example = "ross hotel")
    private String name;

    @Schema(example = "ross hotel is very nice")
    private String description;

    @NotEmpty
    @Schema(example = "83768888")
    private String phone;

    @Email
    @NotEmpty
    @Schema(example = "ross@hotel.com")
    private String email;

    @NotNull
    @Schema(example = "200")
    private Integer totalRoomQuantity;

    @NotNull
    @Schema(example = "2023-05")
    private YearMonth whenBuilt;

    @NotNull
    @Schema(example = "2023-05")
    private YearMonth lastRenovation;

    @NotNull
    @Schema(example = "3")
    private Integer starRating;

    @NotNull
    @Schema(example = "800")
    private Integer buildingArea;


}
