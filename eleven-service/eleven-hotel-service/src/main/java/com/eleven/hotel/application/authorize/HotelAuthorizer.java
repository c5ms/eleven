package com.eleven.hotel.application.authorize;

import com.eleven.hotel.domain.model.hotel.Hotel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelAuthorizer {

    public boolean isAccessible(Hotel hotel) {
        return true;
    }

    public boolean isAccessible(String hotelId) {
        return true;
    }
}
