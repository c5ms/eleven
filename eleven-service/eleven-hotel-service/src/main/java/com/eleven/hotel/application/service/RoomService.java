package com.eleven.hotel.application.service;

import com.eleven.core.application.authorize.NoPrincipalException;
import com.eleven.hotel.application.command.RoomCreateCommand;
import com.eleven.hotel.application.command.RoomUpdateCommand;
import com.eleven.hotel.application.support.HotelContext;
import com.eleven.hotel.domain.manager.RoomManager;
import com.eleven.hotel.domain.model.hotel.*;
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

    @Transactional(readOnly = true)
    public Collection<Room> listRoom(Long hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }

    @Transactional(readOnly = true)
    public Optional<Room> readRoom(RoomKey roomKey) {
        return roomRepository.findByRoomKey(roomKey);
    }

    @Transactional(rollbackFor = Exception.class)
    public Room createRoom(Long hotelId, RoomCreateCommand command) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(NoPrincipalException::new);

        // create the room
        var room = Room.of(hotel.getHotelId(), command.getBasic(), command.getRestriction(),command.getStayPeriod(), command.getCount());
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

        Optional.ofNullable(command.getBasic()).ifPresent(room::setBasic);
        Optional.ofNullable(command.getRestriction()).ifPresent(room::setOccupancy);
        roomManager.validate(room);
        roomRepository.saveAndFlush(room);

        // todo align the inventory

        return room;
    }

}
