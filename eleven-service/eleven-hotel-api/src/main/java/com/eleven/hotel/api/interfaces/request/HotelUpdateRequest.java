package com.eleven.hotel.api.interfaces.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class HotelUpdateRequest {

    @Schema(example = "ross hotel")
    private String name;

    @Schema(example = "ross hotel is very nice")
    private String description;

    @Schema(example = "http://xxx.gif")
    private String headPicUrl;

    @Schema(example = "200")
    private Integer totalRooms;

    @Schema(example = "83768888")
    private String tel;

    @Schema(example = "ross@hotel.com")
    private String email;

    @Schema(type = "string",format = "time",example = "08:30:00")
    private LocalTime checkIn;

    @Schema(type = "string",format = "time",example = "20:30:00")
    private LocalTime checkOut;

    @Schema(example = "辽宁")
    private String province;

    @Schema(example = "大连")
    private String city;

    @Schema(example = "开发区")
    private String district;

    @Schema(example = "海清岛")
    private String street;

    @Schema(example = "4#301")
    private String address;

    @Schema(example = "100.56666")
    private Double lat;

    @Schema(example = "110.9876")
    private Double lng;
}
