package com.eleven.booking.infrastructure;

import com.eleven.booking.domain.model.booking.Hotel;
import com.eleven.hotel.api.domain.values.SaleState;
import com.eleven.hotel.api.interfaces.dto.HotelDto;

public class RemoteHotel implements Hotel {

    private final HotelDto dto;

    public RemoteHotel(HotelDto dto) {
        this.dto = dto;
    }

    @Override
    public SaleState getSaleState() {
        return dto.getState();
    }
}
