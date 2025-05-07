package com.motiveschina.hotel.features.booking.support;


import com.motiveschina.hotel.features.booking.Hotel;
import com.motiveschina.hotel.features.hotel.HotelDto;

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
