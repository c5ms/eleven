package com.motiveschina.hotel.features.room;

import java.util.Set;
import com.motiveschina.hotel.features.hotel.values.Occupancy;

public interface RoomPatch {
    RoomStock getStock();

    RoomBasic getBasic();

    Occupancy getOccupancy();

    Set<String> getImages();
}
