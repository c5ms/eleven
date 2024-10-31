package com.eleven.hotel.application.service;

import com.eleven.core.application.ApplicationHelper;
import com.eleven.core.application.security.NoPrincipalException;
import com.eleven.hotel.application.command.RoomCreateCommand;
import com.eleven.hotel.application.command.RoomUpdateCommand;
import com.eleven.hotel.domain.manager.RoomManager;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.domain.model.hotel.RoomRepository;
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
    public Collection<Room> listRoom(Integer hotelId) {
        return roomRepository.findRoomsByHotelId(hotelId);
    }

    @Transactional(readOnly = true)
    public Optional<Room> readRoom(Integer hotelId, Integer roomId) {
        return roomRepository.findByHotelIdAndId(hotelId, roomId);
    }

    @Transactional(rollbackFor = Exception.class)
    public Room createRoom(Integer hotelId, RoomCreateCommand command) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(NoPrincipalException::new);
        var room = new Room(hotel.getId(), command.getBasic(), command.getRestriction(), command.getChargeType());
        roomManager.validate(room);
        roomRepository.save(room);
        return room;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRoom(Integer hotelId, Integer roomId) {
        var room = roomRepository.findByHotelIdAndId(hotelId, roomId).orElseThrow(ApplicationHelper::noPrincipalException);
        roomRepository.delete(room);
    }

    @Transactional(rollbackFor = Exception.class)
    public Room updateRoom(Integer hotelId, Integer roomId, RoomUpdateCommand command) {
        var room = roomRepository.findByHotelIdAndId(hotelId, roomId).orElseThrow(ApplicationHelper::noPrincipalException);

        Optional.ofNullable(command.getChargeType()).ifPresent(room::setChargeType);
        Optional.ofNullable(command.getBasic()).ifPresent(room::setBasic);
        Optional.ofNullable(command.getRestriction()).ifPresent(room::setRestriction);
        roomManager.validate(room);
        roomRepository.save(room);
        return room;
    }

}
