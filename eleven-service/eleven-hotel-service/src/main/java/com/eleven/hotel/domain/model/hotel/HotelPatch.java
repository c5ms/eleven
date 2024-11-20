package com.eleven.hotel.domain.model.hotel;

public interface HotelPatch {
    HotelBasic getBasic();

    com.eleven.hotel.domain.values.Address getAddress();

    com.eleven.hotel.domain.values.Position getPosition();

    com.eleven.hotel.domain.values.CheckPolicy getCheckPolicy();
}
