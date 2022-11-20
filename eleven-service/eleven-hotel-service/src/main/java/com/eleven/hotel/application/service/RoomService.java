package com.eleven.hotel.application.service;

import com.eleven.hotel.application.command.RoomCreateCommand;
import com.eleven.hotel.application.command.RoomDeleteCommand;
import com.eleven.hotel.domain.model.hotel.HotelRoom;

public interface RoomService {
    HotelRoom createRoom(RoomCreateCommand command);

    void deleteRoom(RoomDeleteCommand command);
}
