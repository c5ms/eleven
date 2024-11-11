package com.eleven.hotel.application.service.manager;

import com.eleven.hotel.application.command.RoomCreateCommand;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.domain.model.hotel.RoomValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomManager {
    private final List<RoomValidator> roomValidators;

    public void validate(Room room) {
        roomValidators.forEach(roomValidator -> roomValidator.validate(room));
    }

    public Room createRoom(Hotel hotel, RoomCreateCommand command) {
        return new Room(hotel.getHotelId(), command.getBasic(), command.getRestriction());
    }

}
