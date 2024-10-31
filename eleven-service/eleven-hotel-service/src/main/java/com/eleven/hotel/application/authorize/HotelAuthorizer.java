package com.eleven.hotel.application.authorize;

import com.eleven.core.application.security.TypedObjectAuthorizer;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.hotel.Plan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelAuthorizer extends TypedObjectAuthorizer<Hotel> {

    @Override
    protected boolean checkIsReadable(Hotel hotel) {
        return true;
    }

    @Override
    protected boolean checkIsWritable(Hotel hotel) {
        return true;
    }
}
