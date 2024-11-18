package com.eleven.hotel.api.interfaces.vo;

import com.eleven.hotel.api.interfaces.constants.HotelConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.YearMonth;

@Data
@Schema(name = "RoomBasic")
@Accessors(chain = true)
public class HotelBasicVo {

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
