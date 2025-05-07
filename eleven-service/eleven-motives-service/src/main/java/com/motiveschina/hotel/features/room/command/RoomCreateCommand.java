package com.motiveschina.hotel.features.room.command;

import java.util.Set;
import com.motiveschina.hotel.features.hotel.values.Occupancy;
import com.motiveschina.hotel.features.room.RoomBasic;
import com.motiveschina.hotel.features.room.RoomStock;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomCreateCommand {
    private RoomBasic basic;
    private RoomStock stock;
    private Occupancy occupancy;
    private Set<String> images;
}
