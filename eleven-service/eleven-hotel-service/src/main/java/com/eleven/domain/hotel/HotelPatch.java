package com.eleven.domain.hotel;

import com.eleven.domain.hotel.values.Address;
import com.eleven.domain.hotel.values.CheckPolicy;
import com.eleven.domain.hotel.values.HotelBasic;
import com.eleven.domain.hotel.values.Position;

public interface HotelPatch {
    HotelBasic getBasic();

    Address getAddress();

    Position getPosition();

    CheckPolicy getCheckPolicy();
}
