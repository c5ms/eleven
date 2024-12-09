package com.eleven.hotel.domain.manager;

import com.eleven.hotel.domain.model.room.Room;
import com.eleven.hotel.domain.model.room.RoomValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoomManager {
    private final List<RoomValidator> roomValidators;

    public void validate(Room room) {
        roomValidators.forEach(roomValidator -> roomValidator.validate(room));
    }


}
