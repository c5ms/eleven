package com.eleven.hotel.endpoint.convert;

import com.eleven.hotel.api.endpoint.model.RoomDto;
import com.eleven.hotel.api.endpoint.request.RoomCreateRequest;
import com.eleven.hotel.api.endpoint.request.RoomUpdateRequest;
import com.eleven.hotel.application.command.RoomCreateCommand;
import com.eleven.hotel.application.command.RoomUpdateCommand;
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
            .setSaleState(room.getSaleState())
            .setName(room.getBasic().getName())
            .setType(room.getBasic().getLevel())
            .setDesc(room.getBasic().getDesc())
            .setHeadPicUrl(room.getBasic().getHeadPicUrl())
            .setMinPerson(room.getRestriction().getMinPerson())
            .setMaxPerson(room.getRestriction().getMaxPerson())
            ;
    }

    public List<RoomDto> toDto(List<Room> rooms) {
        return rooms.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    public RoomCreateCommand toCommand(RoomCreateRequest request) {
        return RoomCreateCommand.builder()
            .basic(new Room.RoomBasic(request.getName(), request.getLevel(), request.getDesc(), request.getHeadPicUrl()))
            .restriction(new Room.RoomRestriction(request.getMinPerson(), request.getMaxPerson()))
            .build();
    }

    public RoomUpdateCommand toCommand(RoomUpdateRequest request) {
        return RoomUpdateCommand.builder()
            .chargeType(request.getChargeType())
            .basic(new Room.RoomBasic(request.getName(), request.getType(), request.getDesc(), request.getHeadPicUrl()))
            .restriction(new Room.RoomRestriction(request.getMinPerson(), request.getMaxPerson()))
            .build();
    }
}
