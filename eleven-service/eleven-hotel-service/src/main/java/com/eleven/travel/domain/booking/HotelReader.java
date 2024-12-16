package com.eleven.travel.domain.booking;


import java.util.Optional;

public interface HotelReader {

    Optional<Hotel> readHotel(Long hotelId);

}
