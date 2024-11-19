package com.eleven.hotel.api.interfaces.model.room;

import com.eleven.hotel.api.interfaces.values.DateRangeVo;
import com.eleven.hotel.api.interfaces.values.OccupancyVo;
import com.eleven.hotel.api.interfaces.values.RoomBasicVo;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RoomCreateRequest {

    @NotNull
    @JsonUnwrapped(prefix = "room_")
    private RoomBasicVo basic;

    @NotNull
    @JsonUnwrapped(prefix = "available_")
    private DateRangeVo availablePeriod;

    @NotNull
    @JsonUnwrapped(prefix = "occupancy_")
    private OccupancyVo occupancy;

    @Min(0)
    @Max(999)
    @Schema(example = "20")
    private Integer quantity;


    @NotEmpty
    @Size(min = 1, max = 10)
    private Set<String> images = new HashSet<>();
}
