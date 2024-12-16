package com.eleven.travel.domain.hotel;

import com.eleven.travel.domain.hotel.values.Address;
import com.eleven.travel.domain.hotel.values.CheckPolicy;
import com.eleven.travel.domain.hotel.values.HotelBasic;
import com.eleven.travel.domain.hotel.values.Position;

public interface HotelPatch {
    HotelBasic getBasic();

    Address getAddress();

    Position getPosition();

    CheckPolicy getCheckPolicy();
}
