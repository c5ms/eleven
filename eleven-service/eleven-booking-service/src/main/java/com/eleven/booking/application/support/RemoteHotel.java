package com.eleven.booking.application.support;

import com.eleven.booking.domain.model.booking.Hotel;
import com.eleven.hotel.api.domain.model.SaleState;
import com.eleven.hotel.api.endpoint.model.HotelDto;

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
