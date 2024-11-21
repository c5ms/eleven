package com.eleven.hotel.api.interfaces.model.room;

import com.eleven.hotel.api.interfaces.values.OccupancyVo;
import com.eleven.hotel.api.interfaces.values.RoomBasicVo;
import com.eleven.hotel.api.interfaces.values.RoomStockVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Schema(name = "Room")
@Accessors(chain = true)
public final class RoomDto {
    private Long hotelId;
    private Long roomId;

    @JsonUnwrapped(prefix = "room")
    private RoomBasicVo basic;

    @JsonUnwrapped(prefix = "occupancy")
    private OccupancyVo occupancy;

    @NotNull
    @JsonUnwrapped(prefix = "stock")
    private RoomStockVo stock;

    private Set<String> images = new HashSet<>();
}
