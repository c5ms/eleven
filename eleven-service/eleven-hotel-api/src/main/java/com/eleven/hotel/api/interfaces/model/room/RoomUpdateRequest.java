package com.eleven.hotel.api.interfaces.model.room;

import com.eleven.hotel.api.interfaces.values.OccupancyVo;
import com.eleven.hotel.api.interfaces.values.RoomBasicVo;
import com.eleven.hotel.api.interfaces.values.RoomStockVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public record RoomUpdateRequest {

    @NotNull
    @JsonUnwrapped(prefix = "room")
    private RoomBasicVo basic;

    @NotNull
    @JsonUnwrapped(prefix = "stock")
    private RoomStockVo stock;

    @NotNull
    @JsonUnwrapped(prefix = "occupancy")
    private OccupancyVo occupancy;

    @NotEmpty
    @Size(min = 1, max = 10)
    private Set<String> images = new HashSet<>();

}
