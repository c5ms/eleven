package com.eleven.hotel.application.service;

import com.eleven.core.application.authorize.NoPrincipalException;
import com.eleven.hotel.application.command.RoomCreateCommand;
import com.eleven.hotel.application.command.RoomUpdateCommand;
import com.eleven.hotel.application.query.RoomQuery;
import com.eleven.hotel.application.support.HotelContext;
import com.eleven.hotel.domain.model.hotel.*;
import com.eleven.hotel.domain.service.RoomManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomManager roomManager;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final InventoryRepository inventoryRepository;

    @Transactional(rollbackFor = Exception.class)
    public Room createRoom(Long hotelId, RoomCreateCommand command) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(NoPrincipalException::new);

        // create the room
        var room = Room.builder()
                .hotelId(hotel.getHotelId())
                .basic(command.getBasic())
                .occupancy(command.getOccupancy())
                .availablePeriod(command.getAvailablePeriod())
                .quantity(command.getQuantity())
                .images(command.getImages())
                .build();

        roomManager.validate(room);
        roomRepository.save(room);

        // initialize the inventories
        var inventories = room.createInventories();
        inventoryRepository.saveAllAndFlush(inventories);

        return room;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRoom(RoomKey roomKey) {
        var room = roomRepository.findByRoomKey(roomKey).orElseThrow(HotelContext::noPrincipalException);
        roomRepository.delete(room);

        // drop the inventories
        inventoryRepository.deleteByRoomKey(roomKey);
    }

    @Transactional(rollbackFor = Exception.class)
    public Room updateRoom(RoomKey roomKey, RoomUpdateCommand command) {
        var room = roomRepository.findByRoomKey(roomKey).orElseThrow(HotelContext::noPrincipalException);
        room.setBasic(command.getBasic());
        room.setOccupancy(command.getOccupancy());
        room.setAvailablePeriod(command.getAvailablePeriod());
        room.setQuantity(command.getQuantity());
        room.setImages(command.getImages());

        roomManager.validate(room);
        roomRepository.saveAndFlush(room);

        // todo align the inventory

        return room;
    }

}
