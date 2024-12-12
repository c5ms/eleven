package com.eleven.domain.booking.support;

import com.eleven.domain.booking.Hotel;
import com.eleven.domain.booking.HotelReader;
import com.eleven.domain.hotel.HotelClient;
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
