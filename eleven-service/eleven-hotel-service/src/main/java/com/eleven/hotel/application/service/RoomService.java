package com.eleven.hotel.application.service;

import com.eleven.core.application.authorize.NoPrincipalException;
import com.eleven.hotel.application.command.RoomCreateCommand;
import com.eleven.hotel.application.command.RoomUpdateCommand;
import com.eleven.hotel.application.support.HotelContext;
import com.eleven.hotel.domain.manager.HotelManager;
import com.eleven.hotel.domain.model.hotel.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class RoomService {

    private final HotelManager hotelManager;

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final InventoryRepository inventoryRepository;

    public Room createRoom(Long hotelId, RoomCreateCommand command) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(NoPrincipalException::new);

        var room = Room.builder()
                .hotelId(hotel.getHotelId())
                .basic(command.getBasic())
                .occupancy(command.getOccupancy())
                .stock(command.getStock())
                .images(command.getImages())
                .build();

        hotelManager.validate(room);
        roomRepository.save(room);
        return room;
    }

    public void deleteRoom(RoomKey roomKey) {
        var room = roomRepository.findByRoomKey(roomKey).orElseThrow(HotelContext::noPrincipalException);
        roomRepository.delete(room);
        inventoryRepository.deleteByRoomKey(roomKey);
    }

    public Room updateRoom(RoomKey roomKey, RoomUpdateCommand command) {
        var room = roomRepository.findByRoomKey(roomKey).orElseThrow(HotelContext::noPrincipalException);

        var patch = RoomPatch.builder()
                .basic(command.getBasic())
                .images(command.getImages())
                .stock(command.getStock())
                .occupancy(command.getOccupancy())
                .build();

        room.update(patch);
        hotelManager.validate(room);
        roomRepository.saveAndFlush(room);
        return room;
    }




}
