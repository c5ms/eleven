package com.motiveschina.hotel.features.hotel;

import com.motiveschina.hotel.features.hotel.values.Address;
import com.motiveschina.hotel.features.hotel.values.CheckPolicy;
import com.motiveschina.hotel.features.hotel.values.HotelBasic;
import com.motiveschina.hotel.features.hotel.values.Position;

public interface HotelPatch {
    HotelBasic getBasic();

    Address getAddress();

    Position getPosition();

    CheckPolicy getCheckPolicy();
}
