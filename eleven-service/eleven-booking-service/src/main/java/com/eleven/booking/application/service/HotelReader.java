package com.eleven.booking.application.service;

import com.eleven.booking.domain.model.booking.Hotel;

import java.util.Optional;

public interface HotelReader {

    Optional<Hotel> readHotel(Long hotelId);

}
