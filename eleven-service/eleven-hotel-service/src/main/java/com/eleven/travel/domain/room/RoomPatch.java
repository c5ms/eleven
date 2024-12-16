package com.eleven.travel.domain.room;

import com.eleven.travel.domain.hotel.values.Occupancy;

import java.util.Set;

public interface RoomPatch {
    RoomStock getStock();

    RoomBasic getBasic();

    Occupancy getOccupancy();

    Set<String> getImages();
}
