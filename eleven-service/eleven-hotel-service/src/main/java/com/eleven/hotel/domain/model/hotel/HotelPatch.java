package com.eleven.hotel.domain.model.hotel;

import com.eleven.hotel.domain.values.Address;
import com.eleven.hotel.domain.values.CheckPolicy;
import com.eleven.hotel.domain.values.HotelBasic;
import com.eleven.hotel.domain.values.Position;

public interface HotelPatch {
    HotelBasic getBasic();

    Address getAddress();

    Position getPosition();

    CheckPolicy getCheckPolicy();
}
