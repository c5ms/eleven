package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.domain.NoEntityFoundException;

public class HotelNotFoundException extends NoEntityFoundException {

    public HotelNotFoundException(String hotelId) {
        super("not found hotel with id " + hotelId);
    }
}
