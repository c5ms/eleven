package com.eleven.hotel.application.service;

import com.eleven.core.application.authorize.NoPrincipalException;
import com.eleven.hotel.application.command.RoomCreateCommand;
import com.eleven.hotel.application.command.RoomUpdateCommand;
import com.eleven.hotel.application.support.HotelContext;
import com.eleven.hotel.domain.manager.HotelManager;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.model.inventory.RoomInventoryRepository;
import com.eleven.hotel.domain.model.room.Room;
import com.eleven.hotel.domain.model.room.RoomKey;
import com.eleven.hotel.domain.model.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class RoomService {

    private final HotelManager hotelManager;

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomInventoryRepository roomInventoryRepository;

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
        roomInventoryRepository.deleteByRoomKey(roomKey);
    }

    public Room updateRoom(RoomKey roomKey, RoomUpdateCommand command) {
        var room = roomRepository.findByRoomKey(roomKey).orElseThrow(HotelContext::noPrincipalException);
        room.update(command);
        hotelManager.validate(room);
        roomRepository.saveAndFlush(room);
        return room;
    }


}
