package com.eleven.hotel.endpoint.convert;

import com.eleven.hotel.api.endpoint.model.RoomDto;
import com.eleven.hotel.domain.model.hotel.Room;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoomConvertor {
    public final ValuesConvertor values;

    public RoomDto toDto(Room room) {
        return new RoomDto()
            .setRoomId(room.getRoomId())
            .setHotelId(room.getHotelId())
            .setChargeType(room.getChargeType())
            .setSaleState(room.getSaleState())
            .setName(room.getDescription().getName())
            .setType(room.getDescription().getType())
            .setDesc(room.getDescription().getDesc())
            .setHeadPicUrl(room.getDescription().getHeadPicUrl())
            .setMinPerson(room.getRestriction().getMinPerson())
            .setMaxPerson(room.getRestriction().getMaxPerson())
            ;
    }

    public List<RoomDto> toDto(List<Room> rooms) {
        return rooms.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }
}
