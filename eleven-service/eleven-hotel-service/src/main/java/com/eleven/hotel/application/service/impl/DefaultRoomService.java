package com.eleven.hotel.application.service.impl;

import com.eleven.hotel.application.command.RoomCreateCommand;
import com.eleven.hotel.application.command.RoomDeleteCommand;
import com.eleven.hotel.application.command.RoomUpdateCommand;
import com.eleven.hotel.application.service.RoomService;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.model.room.Room;
import com.eleven.hotel.domain.model.room.RoomManager;
import com.eleven.hotel.domain.model.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
class DefaultRoomService implements RoomService {

    private final RoomManager roomManager;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    @Override
    public Room createRoom(RoomCreateCommand command) {
        var roomId = roomManager.nextRoomId();
        var hotel = hotelRepository.requireById(command.getHotelId());
        var room = new Room(roomId, hotel.getId(), command.getDescription(), command.getRestriction(), command.getChargeType());
        roomManager.validate(room);
        roomRepository.save(room);
        return room;
    }

    @Override
    public void deleteRoom(RoomDeleteCommand command) {
        var room = roomRepository.requireById(command.getRoomId());
        roomRepository.delete(room);
    }

    @Override
    public Room updateRoom(RoomUpdateCommand command) {
        var room = roomRepository.requireById(command.getRoomId());
        room.update(command.getChargeType(), command.getDescription(), command.getRestriction());
        roomManager.validate(room);
        roomRepository.save(room);
        return room;
    }

}
