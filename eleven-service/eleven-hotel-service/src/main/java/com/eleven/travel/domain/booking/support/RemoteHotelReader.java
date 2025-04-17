package com.eleven.travel.domain.booking.support;

import com.eleven.travel.domain.booking.Hotel;
import com.eleven.travel.domain.booking.HotelReader;
import com.eleven.travel.domain.hotel.HotelClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RemoteHotelReader implements HotelReader {

    private final HotelClient hotelClient;

    @Override
    public Optional<Hotel> readHotel(Long hotelId) {
        return hotelClient.readHotel(hotelId)
            .map(RemoteHotel::new)
            ;
    }

}
