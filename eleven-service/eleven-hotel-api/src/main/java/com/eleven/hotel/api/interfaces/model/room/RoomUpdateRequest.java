package com.eleven.hotel.api.interfaces.model.room;

import com.eleven.hotel.api.interfaces.values.DateRangeVo;
import com.eleven.hotel.api.interfaces.values.OccupancyVo;
import com.eleven.hotel.api.interfaces.values.RoomBasicVo;
import com.eleven.hotel.api.interfaces.values.RoomStockVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RoomUpdateRequest {

    @NotNull
    @JsonUnwrapped(prefix = "room_")
    private RoomBasicVo basic;

    @NotNull
    @JsonUnwrapped(prefix = "stock_")
    private RoomStockVo stock;

    @NotNull
    @JsonUnwrapped(prefix = "occupancy_")
    private OccupancyVo occupancy;

    @NotEmpty
    @Size(min = 1, max = 10)
    private Set<String> images = new HashSet<>();

}
