package com.eleven.hotel.application.convert;

import com.eleven.hotel.api.application.view.RoomDto;
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

    public RoomDto toDto(Room room) {
        return new RoomDto()
            .setId(room.getId())
            .setChargeType(room.getChargeType())
            .setSaleState(room.getSaleState())
            .setName(room.getDesc().getName())
            .setType(room.getDesc().getType())
            .setCount(room.getStock().getCount())
            .setDesc(room.getDesc().getDesc())
            .setHeadPicUrl(room.getDesc().getHeadPicUrl())
            .setMinPerson(room.getRestrict().getMinPerson())
            .setMaxPerson(room.getRestrict().getMaxPerson())
            ;
    }

    public List<RoomDto> toDto(List<Room> rooms) {
        return rooms.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }
}
