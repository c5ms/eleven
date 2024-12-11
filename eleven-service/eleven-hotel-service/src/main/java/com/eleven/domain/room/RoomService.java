package com.eleven.domain.room;

import com.eleven.core.application.authorize.NoPrincipalException;
import com.eleven.core.support.ContextSupport;
import com.eleven.domain.hotel.HotelRepository;
import com.eleven.domain.inventory.RoomInventoryRepository;
import com.eleven.domain.room.command.RoomCreateCommand;
import com.eleven.domain.room.command.RoomUpdateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class RoomService {

    private final RoomManager roomManager;

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

        roomManager.validate(room);
        roomRepository.save(room);
        return room;
    }

    public void deleteRoom(RoomKey roomKey) {
        var room = roomRepository.findByRoomKey(roomKey).orElseThrow(ContextSupport::noPrincipalException);
        roomRepository.delete(room);
        roomInventoryRepository.deleteByRoomKey(roomKey);
    }

    public Room updateRoom(RoomKey roomKey, RoomUpdateCommand command) {
        var room = roomRepository.findByRoomKey(roomKey).orElseThrow(ContextSupport::noPrincipalException);
        room.update(command);
        roomManager.validate(room);
        roomRepository.saveAndFlush(room);
        return room;
    }


}
