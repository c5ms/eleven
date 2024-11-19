package com.eleven.hotel.interfaces.converter;

import com.eleven.hotel.api.interfaces.values.RoomBasicVo;
import com.eleven.hotel.api.interfaces.model.room.RoomCreateRequest;
import com.eleven.hotel.api.interfaces.model.room.RoomDto;
import com.eleven.hotel.api.interfaces.model.room.RoomUpdateRequest;
import com.eleven.hotel.api.interfaces.values.DateRangeVo;
import com.eleven.hotel.api.interfaces.values.OccupancyVo;
import com.eleven.hotel.application.command.RoomCreateCommand;
import com.eleven.hotel.application.command.RoomUpdateCommand;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.domain.model.hotel.RoomBasic;
import com.eleven.hotel.domain.values.DateRange;
import com.eleven.hotel.domain.values.Occupancy;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomConverter {
    private final ModelMapper modelMapper;

    public RoomDto toDto(Room room) {
        return new RoomDto()
                .setRoomId(room.getRoomId())
                .setQuantity(room.getQuantity())
                .setImages(room.getImages())
                .setAvailablePeriod(modelMapper.map(room.getAvailablePeriod(), DateRangeVo.class))
                .setBasic(modelMapper.map(room.getBasic(), RoomBasicVo.class))
                .setOccupancy(modelMapper.map(room.getOccupancy(), OccupancyVo.class));
    }

    public RoomUpdateCommand toCommand(RoomUpdateRequest request) {
        return RoomUpdateCommand.builder()
                .quantity(request.getQuantity())
                .images(request.getImages())
                .availablePeriod(modelMapper.map(request.getAvailablePeriod(), DateRange.class))
                .basic(modelMapper.map(request.getBasic(), RoomBasic.class))
                .occupancy(modelMapper.map(request.getOccupancy(), Occupancy.class))
                .build();
    }

    public RoomCreateCommand toCommand(RoomCreateRequest request) {
        return RoomCreateCommand.builder()
                .quantity(request.getQuantity())
                .images(request.getImages())
                .availablePeriod(modelMapper.map(request.getAvailablePeriod(), DateRange.class))
                .basic(modelMapper.map(request.getBasic(), RoomBasic.class))
                .occupancy(modelMapper.map(request.getOccupancy(), Occupancy.class))
                .build();
    }
}
