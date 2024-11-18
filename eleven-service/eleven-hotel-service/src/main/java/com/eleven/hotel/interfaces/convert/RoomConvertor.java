package com.eleven.hotel.interfaces.convert;

import com.eleven.hotel.api.interfaces.dto.RoomDto;
import com.eleven.hotel.api.interfaces.request.RoomCreateRequest;
import com.eleven.hotel.api.interfaces.request.RoomUpdateRequest;
import com.eleven.hotel.api.interfaces.vo.DateRangeVo;
import com.eleven.hotel.application.command.RoomCreateCommand;
import com.eleven.hotel.application.command.RoomUpdateCommand;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.domain.model.hotel.RoomBasic;
import com.eleven.hotel.domain.model.hotel.RoomOccupancy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoomConvertor {
    public final ValuesConvertor values;

    public RoomDto toDto(Room room) {
        return new RoomDto()
                .setRoomId(room.getRoomId())
                .setHotelId(room.getHotelId())
                .setQuantity(room.getQuantity())
                .setImages(room.getImages())
                .setAvailablePeriod(DateRangeVo.from(room.getAvailablePeriod()))
                .setName(room.getBasic().getName())
                .setDesc(room.getBasic().getDesc())
                .setArea(room.getBasic().getArea())
                .setFloor(room.getBasic().getFloor())
                .setMinPerson(room.getOccupancy().getMinPerson())
                .setMaxPerson(room.getOccupancy().getMaxPerson())
                ;
    }

    public RoomCreateCommand toCommand(RoomCreateRequest request) {
        return RoomCreateCommand.builder()
                .basic(new RoomBasic(request.getName(), request.getDesc(), request.getArea(), request.getFloor()))
                .restriction(new RoomOccupancy(request.getMinPerson(), request.getMaxPerson()))
                .availablePeriod(request.getAvailablePeriod().toDateRange())
                .images(request.getImages())
                .quantity(request.getQuantity())
                .build();
    }

    public RoomUpdateCommand toCommand(RoomUpdateRequest request) {
        return RoomUpdateCommand.builder()
                .basic(new RoomBasic(request.getName(), request.getDesc(), request.getArea(), request.getFloor()))
                .restriction(new RoomOccupancy(request.getMinPerson(), request.getMaxPerson()))
                .availablePeriod(request.getAvailablePeriod().toDateRange())
                .images(request.getImages())
                .quantity(request.getQuantity())
                .build();
    }
}
