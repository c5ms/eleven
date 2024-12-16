package com.eleven.travel.domain.booking.support;


import com.eleven.travel.domain.booking.Hotel;
import com.eleven.travel.domain.hotel.HotelDto;

public class RemoteHotel implements Hotel {

    private final HotelDto dto;

    public RemoteHotel(HotelDto dto) {
        this.dto = dto;
    }

    @Override
    public boolean isActive() {
        return dto.getActive();
    }
}
