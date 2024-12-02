package com.eleven.hotel.application.command;

import com.eleven.hotel.domain.model.room.RoomPatch;
import com.eleven.hotel.domain.values.Occupancy;
import com.eleven.hotel.domain.values.RoomBasic;
import com.eleven.hotel.domain.values.RoomStock;
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
