package com.eleven.travel.domain.room.command;

import com.eleven.travel.domain.hotel.values.Occupancy;
import com.eleven.travel.domain.room.RoomBasic;
import com.eleven.travel.domain.room.RoomPatch;
import com.eleven.travel.domain.room.RoomStock;
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
