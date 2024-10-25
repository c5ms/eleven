package com.eleven.hotel.domain.model.room;

import com.eleven.core.domain.DomainUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomManager {
    private final List<RoomValidator> roomValidators;


    public String nextRoomId() {
        return DomainUtils.nextId();
    }

    public void validate(Room room) {
        roomValidators.forEach(roomValidator -> roomValidator.validate(room));
    }
}
