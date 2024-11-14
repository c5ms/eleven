package com.eleven.hotel.interfaces.convert;

import com.eleven.core.domain.values.DateRange;
import com.eleven.hotel.api.interfaces.model.RoomDto;
import com.eleven.hotel.api.interfaces.request.RoomCreateRequest;
import com.eleven.hotel.api.interfaces.request.RoomUpdateRequest;
import com.eleven.hotel.application.command.RoomCreateCommand;
import com.eleven.hotel.application.command.RoomUpdateCommand;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.domain.model.hotel.RoomBasic;
import com.eleven.hotel.domain.model.hotel.RoomRestriction;
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
                .setName(room.getBasic().getName())
                .setType(room.getBasic().getLevel())
                .setDesc(room.getBasic().getDesc())
                .setHeadPicUrl(room.getBasic().getHeadPicUrl())
                .setMinPerson(room.getRestriction().getMinPerson())
                .setMaxPerson(room.getRestriction().getMaxPerson())
                .setCount(room.getCount())
                .setStayPeriod(room.getStayPeriod())
                ;
    }

    public List<RoomDto> toDto(List<Room> rooms) {
        return rooms.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public RoomCreateCommand toCommand(RoomCreateRequest request) {
        return RoomCreateCommand.builder()
                .basic(new RoomBasic(request.getName(), request.getLevel(), request.getDesc(), request.getHeadPicUrl()))
                .restriction(new RoomRestriction(request.getMinPerson(), request.getMaxPerson()))
                .stayPeriod(DateRange.of(request.getStayStartDate(), request.getStayEndDate()))
                .count(request.getCount())
                .build();
    }

    public RoomUpdateCommand toCommand(RoomUpdateRequest request) {
        return RoomUpdateCommand.builder()
                .basic(new RoomBasic(request.getName(), request.getType(), request.getDesc(), request.getHeadPicUrl()))
                .restriction(new RoomRestriction(request.getMinPerson(), request.getMaxPerson()))
                .stayPeriod(DateRange.of(request.getStayStartDate(), request.getStayEndDate()))
                .count(request.getCount())
                .build();
    }
}
