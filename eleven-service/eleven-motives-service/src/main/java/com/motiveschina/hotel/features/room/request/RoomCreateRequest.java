package com.motiveschina.hotel.features.room.request;

import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.motiveschina.hotel.features.hotel.vo.OccupancyVo;
import com.motiveschina.hotel.features.room.vo.RoomBasicVo;
import com.motiveschina.hotel.features.room.vo.RoomStockVo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

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
