package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.domain.NoEntityException;

public class HotelNotFoundException extends NoEntityException {

    public HotelNotFoundException(String hotelId) {
        super("not found hotel with id " + hotelId);
    }
}
