package com.eleven.domain.room.request;

import com.eleven.domain.hotel.vo.OccupancyVo;
import com.eleven.domain.room.RoomBasicVo;
import com.eleven.domain.room.RoomStockVo;
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
public final class RoomUpdateRequest {

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
