package com.eleven.hotel.application.service;

import com.eleven.hotel.application.command.RoomCreateCommand;
import com.eleven.hotel.application.command.RoomDeleteCommand;
import com.eleven.hotel.application.command.RoomUpdateCommand;
import com.eleven.hotel.domain.model.room.Room;

public interface RoomService {
    Room createRoom(RoomCreateCommand command);

    Room updateRoom(RoomUpdateCommand command);

    void deleteRoom(RoomDeleteCommand command);
}
