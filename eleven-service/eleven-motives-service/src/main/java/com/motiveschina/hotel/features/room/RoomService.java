package com.motiveschina.hotel.features.room;

import com.eleven.framework.domain.NoDomainEntityException;
import com.motiveschina.hotel.common.support.ContextSupport;
import com.motiveschina.hotel.features.hotel.HotelRepository;
import com.motiveschina.hotel.features.inventory.RoomInventoryRepository;
import com.motiveschina.hotel.features.room.command.RoomCreateCommand;
import com.motiveschina.hotel.features.room.command.RoomUpdateCommand;
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
        var hotel = hotelRepository.findById(hotelId).orElseThrow(NoDomainEntityException::instance);

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
