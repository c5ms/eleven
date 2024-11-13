package com.eleven.hotel.application.service;

import com.eleven.core.application.authorize.NoPrincipalException;
import com.eleven.hotel.application.command.RoomCreateCommand;
import com.eleven.hotel.application.command.RoomUpdateCommand;
import com.eleven.hotel.domain.manager.RoomManager;
import com.eleven.hotel.application.support.HotelContext;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.model.room.Room;
import com.eleven.hotel.domain.model.room.RoomRepository;
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

    @Transactional(readOnly = true)
    public Collection<Room> listRoom(Long hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }

    @Transactional(readOnly = true)
    public Optional<Room> readRoom(Long hotelId, Long roomId) {
        return roomRepository.findByHotelIdAndRoomId(hotelId, roomId);
    }

    @Transactional(rollbackFor = Exception.class)
    public Room createRoom(Long hotelId, RoomCreateCommand command) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(NoPrincipalException::new);
        var room = new Room(hotel.getHotelId(), command.getBasic(), command.getRestriction());
        roomManager.validate(room);
        roomRepository.saveAndFlush(room);
        return room;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRoom(Long hotelId, Long roomId) {
        var room = roomRepository.findByHotelIdAndRoomId(hotelId, roomId).orElseThrow(HotelContext::noPrincipalException);
        roomRepository.delete(room);
    }

    @Transactional(rollbackFor = Exception.class)
    public Room updateRoom(Long hotelId, Long roomId, RoomUpdateCommand command) {
        var room = roomRepository.findByHotelIdAndRoomId(hotelId, roomId).orElseThrow(HotelContext::noPrincipalException);

        Optional.ofNullable(command.getBasic()).ifPresent(room::setBasic);
        Optional.ofNullable(command.getRestriction()).ifPresent(room::setRestriction);
        roomManager.validate(room);
        roomRepository.saveAndFlush(room);
        return room;
    }

}
