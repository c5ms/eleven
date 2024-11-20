package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.domain.values.Occupancy;

import java.util.Set;

public interface RoomPatch {
    RoomStock getStock();

    RoomBasic getBasic();

    Occupancy getOccupancy();

    Set<String> getImages();
}
