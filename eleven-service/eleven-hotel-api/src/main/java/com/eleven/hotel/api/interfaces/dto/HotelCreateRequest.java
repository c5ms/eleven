package com.eleven.hotel.api.interfaces.dto;

import com.eleven.hotel.api.application.constants.HotelConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.YearMonth;

@Getter
@Setter
public class HotelCreateRequest {

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

    @Pattern(regexp = HotelConstants.REGEXP_HOUR_MINUTES)
    @Schema(example = "13:00", format = HotelConstants.FORMAT_HOUR_MINUTES)
    private String checkIn;

    @Pattern(regexp = HotelConstants.REGEXP_HOUR_MINUTES)
    @Schema(example = "11:00", format = HotelConstants.FORMAT_HOUR_MINUTES)
    private String checkOut;

    @Schema(example = "中国大陆")
    private String country;

    @Schema(example = "辽宁")
    private String province;

    @Schema(example = "大连")
    private String city;

    @Schema(example = "开发区")
    private String location;

    @Schema(example = "大连开发区海青岛街道东行300米")
    private String address;

    @Schema(example = "100.56666")
    private Double latitude;

    @Schema(example = "110.9876")
    private Double longitude;

    @Schema(example = "2024-01-01")
    private LocalDate localDate;

}
