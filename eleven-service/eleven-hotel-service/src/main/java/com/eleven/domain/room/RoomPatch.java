package com.eleven.domain.room;

import com.eleven.domain.hotel.values.Occupancy;

import java.util.Set;

public interface RoomPatch {
    RoomStock getStock();

    RoomBasic getBasic();

    Occupancy getOccupancy();

    Set<String> getImages();
}
