package com.eleven.domain.room.command;

import com.eleven.domain.hotel.values.Occupancy;
import com.eleven.domain.room.RoomBasic;
import com.eleven.domain.room.RoomPatch;
import com.eleven.domain.room.RoomStock;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class RoomUpdateCommand implements RoomPatch {
    private RoomBasic basic;
    private RoomStock stock;
    private Occupancy occupancy;
    private Set<String> images;
}
