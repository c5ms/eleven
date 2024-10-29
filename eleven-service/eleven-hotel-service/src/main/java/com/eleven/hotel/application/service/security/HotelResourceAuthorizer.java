package com.eleven.hotel.application.service.security;

import com.eleven.core.application.secure.TypedDomainAuthorizer;
import com.eleven.hotel.domain.model.hotel.Hotel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelResourceAuthorizer extends TypedDomainAuthorizer<Hotel> {

    @Override
    public boolean checkIsReadable(Hotel hotel) {
        return true;
    }

    @Override
    public boolean checkIsWritable(Hotel hotel) {
        return true;
    }
}
