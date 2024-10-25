package com.eleven.hotel.application.service.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelSecurityManager {

    public boolean checkAccessHotel(String hotelId) {
//        throw  new ResourceNofFoundException();
//        return false;
        return true;
    }
}
