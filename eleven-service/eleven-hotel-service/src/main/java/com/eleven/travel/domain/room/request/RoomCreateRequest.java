package com.eleven.travel.domain.room.request;

import com.eleven.travel.domain.hotel.vo.OccupancyVo;
import com.eleven.travel.domain.room.vo.RoomBasicVo;
import com.eleven.travel.domain.room.vo.RoomStockVo;
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
public final class RoomCreateRequest {

    @NotNull
    @JsonUnwrapped(prefix = "room")
    private RoomBasicVo basic;

    @NotNull
    @JsonUnwrapped(prefix = "occupancy")
    private OccupancyVo occupancy;

    @NotNull
    @JsonUnwrapped(prefix = "stock")
    private RoomStockVo stock;

    @NotEmpty
    @Size(min = 1, max = 10)
    private Set<String> images = new HashSet<>();
}
