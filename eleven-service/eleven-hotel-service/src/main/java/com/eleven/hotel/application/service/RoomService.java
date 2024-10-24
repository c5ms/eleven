package com.eleven.hotel.application.service;

import com.eleven.hotel.application.command.RoomCreateCommand;
import com.eleven.hotel.application.command.RoomDeleteCommand;
import com.eleven.hotel.application.command.RoomUpdateCommand;
import com.eleven.hotel.domain.model.hotel.Room;

public interface RoomService {
    Room createRoom(RoomCreateCommand command);

    void deleteRoom(RoomDeleteCommand command);

    Room updateRoom(RoomUpdateCommand command);
}
