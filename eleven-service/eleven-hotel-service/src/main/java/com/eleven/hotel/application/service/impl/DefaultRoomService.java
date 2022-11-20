package com.eleven.hotel.application.service.impl;

import com.eleven.hotel.application.command.RoomCreateCommand;
import com.eleven.hotel.application.command.RoomDeleteCommand;
import com.eleven.hotel.application.service.RoomService;
import com.eleven.hotel.domain.model.hotel.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
class DefaultRoomService implements RoomService {

    private final HotelManager hotelManager;
    private final HotelRoomRepository hotelRoomRepository;
    private final HotelRepository hotelRepository;

    @Override
    public HotelRoom createRoom(RoomCreateCommand command) {
        var id = hotelManager.nextRoomId();
        var hotel = hotelRepository.requireById(command.getHotelId());
        var description = RoomDesc.builder().desc(command.getDesc()).build();
        var room = HotelRoom.create(id, hotel, command.getName(), description, command.getSize(), command.getStock());
        hotelManager.validate(room);
        hotelRoomRepository.save(room);
        return room;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("@hotelSecurityManager.checkAccessHotel(#command.hotelId)")
    public void deleteRoom(RoomDeleteCommand command) {
        var room = hotelRoomRepository.requireById(command.getRoomId());
        hotelRoomRepository.delete(room);
    }

}
