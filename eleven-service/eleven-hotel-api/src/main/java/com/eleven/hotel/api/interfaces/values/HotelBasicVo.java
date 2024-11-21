package com.eleven.hotel.api.interfaces.values;

import com.eleven.hotel.api.interfaces.model.core.AbstractVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;

@Getter
@Setter
@Schema(name = "HotelBasic")
public class HotelBasicVo extends AbstractVo {

    @Schema(example = "ross hotel")
    private String name;

    @Schema(example = "ross hotel is very nice")
    private String description;

    @Schema(example = "83768888")
    private String phone;

    @Schema(example = "ross@hotel.com")
    private String email;

    @Schema(example = "200")
    private Integer totalRoomQuantity;

    @Schema(example = "2023-05")
    private YearMonth whenBuilt;

    @Schema(example = "2023-05")
    private YearMonth lastRenovation;

    @Schema(example = "3")
    private Integer starRating;

    @Schema(example = "800")
    private Integer buildingArea;


}
