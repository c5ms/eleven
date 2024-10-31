package com.eleven.hotel.application.authorize;

import com.eleven.core.application.ApplicationHelper;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HotelSecurity {

    private final HotelRepository hotelRepository;

    public boolean accessHotel(Integer hotelId) {
        return hotelRepository.findById(hotelId)
            .map(ApplicationHelper::mustWritable)
            .orElseThrow(ApplicationHelper::noPrincipalException);
    }

}
