package com.eleven.hotel.api.interfaces.model.room;

import com.eleven.hotel.api.interfaces.values.OccupancyVo;
import com.eleven.hotel.api.interfaces.values.RoomBasicVo;
import com.eleven.hotel.api.interfaces.values.RoomStockVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @JsonUnwrapped(prefix = "occupancy_")
    private OccupancyVo occupancy;

    @NotNull
    @JsonUnwrapped(prefix = "stock_")
    private RoomStockVo stock;

    @NotEmpty
    @Size(min = 1, max = 10)
    private Set<String> images = new HashSet<>();
}
