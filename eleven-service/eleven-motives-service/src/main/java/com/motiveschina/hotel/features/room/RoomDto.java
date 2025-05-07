package com.motiveschina.hotel.features.room;

import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.motiveschina.hotel.features.hotel.vo.OccupancyVo;
import com.motiveschina.hotel.features.room.vo.RoomBasicVo;
import com.motiveschina.hotel.features.room.vo.RoomStockVo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
