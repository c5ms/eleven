package com.eleven.booking.application.support;

import com.eleven.booking.domain.model.booking.Hotel;
import com.eleven.booking.domain.model.booking.HotelReader;
import com.eleven.hotel.api.endpoint.internal.HotelClient;
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
