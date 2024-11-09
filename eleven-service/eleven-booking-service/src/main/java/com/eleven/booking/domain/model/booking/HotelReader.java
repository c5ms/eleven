package com.eleven.booking.domain.model.booking;

import java.util.Optional;

public interface HotelReader {

    Optional<Hotel> readHotel(Long hotelId);

}
