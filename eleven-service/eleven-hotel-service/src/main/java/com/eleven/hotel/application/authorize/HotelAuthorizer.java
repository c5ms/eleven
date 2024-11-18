package com.eleven.hotel.application.authorize;

import com.eleven.core.application.authorize.TypedObjectAuthorizer;
import com.eleven.hotel.domain.model.hotel.Hotel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelAuthorizer extends TypedObjectAuthorizer<Hotel> {

    @Override
    protected boolean checkIsReadable(Hotel hotel) {
        return false;
    }

    @Override
    protected boolean checkIsWritable(Hotel hotel) {
        return false;
    }
}
