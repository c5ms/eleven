package com.eleven.domain.booking.support;


import com.eleven.domain.booking.Hotel;
import com.eleven.domain.hotel.HotelDto;

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
