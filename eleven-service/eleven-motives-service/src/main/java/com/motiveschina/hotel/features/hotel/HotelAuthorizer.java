package com.motiveschina.hotel.features.hotel;

import com.eleven.framework.authorize.TypedObjectAuthorizer;
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
