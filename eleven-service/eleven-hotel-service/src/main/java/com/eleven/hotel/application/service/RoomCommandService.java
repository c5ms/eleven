package com.eleven.hotel.application.service;

import com.eleven.hotel.application.command.RoomCreateCommand;
import com.eleven.hotel.application.command.RoomDeleteCommand;
import com.eleven.hotel.application.command.RoomUpdateCommand;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.domain.model.hotel.RoomManager;
import com.eleven.hotel.domain.model.hotel.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class RoomCommandService {

    private final RoomManager roomManager;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    public Room createRoom(RoomCreateCommand command) {
        var hotel = hotelRepository.require(command.getHotelId());
        var room = roomManager.create(hotel, command.getDescription(), command.getRestriction(), command.getChargeType());
        roomManager.validate(room);
        roomRepository.save(room);
        return room;
    }

    public void deleteRoom(RoomDeleteCommand command) {
        var room = roomRepository.require(command.getHotelId(), command.getRoomId());
        roomRepository.delete(room);
    }

    public Room updateRoom(RoomUpdateCommand command) {
        var room = roomRepository.require(command.getHotelId(), command.getRoomId());
        Optional.ofNullable(command.getChargeType()).ifPresent(room::setChargeType);
        Optional.ofNullable(command.getDescription()).ifPresent(room::setDescription);
        Optional.ofNullable(command.getRestriction()).ifPresent(room::setRestriction);
        roomManager.validate(room);
        roomRepository.save(room);
        return room;
    }

}
