package com.eleven.booking.infrastructure;

import com.eleven.booking.application.service.HotelReader;
import com.eleven.booking.domain.model.booking.Hotel;
import com.eleven.hotel.api.interfaces.client.HotelClient;
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
