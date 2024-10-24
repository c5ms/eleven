package com.eleven.hotel.application.service.impl;

import com.eleven.hotel.application.command.RoomCreateCommand;
import com.eleven.hotel.application.command.RoomDeleteCommand;
import com.eleven.hotel.application.command.RoomUpdateCommand;
import com.eleven.hotel.application.service.RoomService;
import com.eleven.hotel.domain.model.hotel.HotelManager;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.domain.model.hotel.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
class DefaultRoomService implements RoomService {

    private final HotelManager hotelManager;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    @Override
    public Room createRoom(RoomCreateCommand command) {
        var roomId = hotelManager.nextRoomId();
        var hotel = hotelRepository.requireById(command.getHotelId());
        var room = Room.builder()
            .roomId(roomId)
            .hotelId(hotel.getId())
            .desc(command.getDesc())
            .stock(command.getStock())
            .restrict(command.getRestrict())
            .chargeType(command.getChargeType())
            .build();
        hotelManager.validate(room);
        roomRepository.save(room);
        return room;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoom(RoomDeleteCommand command) {
        var room = roomRepository.requireById(command.getRoomId());
        roomRepository.delete(room);
    }

    @Override
    public Room updateRoom(RoomUpdateCommand command) {
        var room = roomRepository.requireById(command.getRoomId());
        room.update(command.getDesc());
        room.update(command.getChargeType());
        room.update(command.getRestrict());
        roomRepository.save(room);
        return room;
    }

}
