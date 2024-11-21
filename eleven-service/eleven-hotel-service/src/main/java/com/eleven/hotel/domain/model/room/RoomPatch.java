package com.eleven.hotel.domain.model.room;

import com.eleven.hotel.domain.values.Occupancy;
import com.eleven.hotel.domain.values.RoomBasic;
import com.eleven.hotel.domain.values.RoomStock;

import java.util.Set;

public interface RoomPatch {
    RoomStock getStock();

    RoomBasic getBasic();

    Occupancy getOccupancy();

    Set<String> getImages();
}
