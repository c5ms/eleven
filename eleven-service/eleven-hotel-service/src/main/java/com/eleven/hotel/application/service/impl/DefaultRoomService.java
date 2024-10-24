package com.eleven.hotel.application.service.impl;

import com.eleven.hotel.application.command.RoomCreateCommand;
import com.eleven.hotel.application.command.RoomDeleteCommand;
import com.eleven.hotel.application.command.RoomUpdateCommand;
import com.eleven.hotel.application.service.RoomService;
import com.eleven.hotel.domain.model.hotel.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.CommonsClientAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
class DefaultRoomService implements RoomService {

    private final HotelManager hotelManager;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final CommonsClientAutoConfiguration commonsClientAutoConfiguration;

    @Override
    public Room createRoom(RoomCreateCommand command) {
        var id = hotelManager.nextRoomId();
        var hotel = hotelRepository.requireById(command.getHotelId());
        var description = RoomDesc.builder()
            .desc(command.getDesc())
            .headPicUrl(command.getHeadPicUrl())
            .build();
        var room = Room.create(id, hotel,
            command.getName(),
            description,
            command.getSize(),
            command.getStock(),
            command.getChargeType());
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
    public void updateRoom(RoomUpdateCommand command) {
        var room = roomRepository.requireById(command.getRoomId());
        room.update(
            command.getName(),
            RoomDesc.builder()
                .headPicUrl(command.getHeadPicUrl())
                .desc(command.getDesc())
                .build(),
            command.getSize(),
            command.getChargeType()
        );
        roomRepository.save(room);
    }

}
