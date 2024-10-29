package com.eleven.hotel.application.secure;

import com.eleven.core.application.secure.TypedDomainAuthorizer;
import com.eleven.hotel.domain.model.hotel.Room;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoomResourceAuthorizer extends TypedDomainAuthorizer<Room> {

    @Override
    public boolean checkIsReadable(Room room) {
        return true;
    }

    @Override
    public boolean checkIsWritable(Room room) {
        return true;
    }
}
