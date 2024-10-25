package com.eleven.hotel.domain.model.room.validate;

import com.eleven.hotel.domain.model.room.Room;
import com.eleven.hotel.domain.model.room.RoomValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoomNameValidator implements RoomValidator {

    @Override
    public void validate(Room room) {
    }

}
