package com.eleven.hotel.application.service;

import com.eleven.hotel.application.support.HotelContext;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HotelSecurity {

    private final HotelRepository hotelRepository;

    public boolean accessHotel(Long hotelId) {
        return hotelRepository.findById(hotelId)
                .map(HotelContext::mustWritable)
                .orElseThrow(HotelContext::noPrincipalException);
    }

}
