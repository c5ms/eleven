package com.motiveschina.hotel.features.booking.support;

import java.util.Optional;
import com.motiveschina.hotel.features.booking.Hotel;
import com.motiveschina.hotel.features.booking.HotelReader;
import com.motiveschina.hotel.features.hotel.HotelClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
