package com.eleven.hotel.application.service.security;

import com.eleven.core.application.security.TypedResourceAuthorizer;
import com.eleven.hotel.domain.model.hotel.Room;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RoomResourceAuthorizer extends TypedResourceAuthorizer<Room>  {

    @Override
    public boolean checkIsReadable(Room room) {
        return true;
    }

    @Override
    public boolean checkIsWritable(Room room) {
        return false;
    }
}
