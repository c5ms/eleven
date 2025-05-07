package com.motiveschina.hotel.features.booking;


import java.util.Optional;

public interface HotelReader {

    Optional<Hotel> readHotel(Long hotelId);

}
