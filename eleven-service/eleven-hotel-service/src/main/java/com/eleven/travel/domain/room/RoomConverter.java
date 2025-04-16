package com.eleven.travel.domain.room;

import com.eleven.travel.domain.hotel.values.Occupancy;
import com.eleven.travel.domain.hotel.vo.OccupancyVo;
import com.eleven.travel.domain.room.command.RoomCreateCommand;
import com.eleven.travel.domain.room.command.RoomUpdateCommand;
import com.eleven.travel.domain.room.request.RoomCreateRequest;
import com.eleven.travel.domain.room.request.RoomUpdateRequest;
import com.eleven.travel.domain.room.vo.RoomBasicVo;
import com.eleven.travel.domain.room.vo.RoomStockVo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomConverter {
    private final ModelMapper modelMapper;

    public RoomDto toDto(Room room) {
        return new RoomDto()
            .setHotelId(room.getHotelId())
            .setRoomId(room.getRoomId())
            .setImages(room.getImages().toSet())
            .setStock(modelMapper.map(room.getStock(), RoomStockVo.class))
            .setBasic(modelMapper.map(room.getBasic(), RoomBasicVo.class))
            .setOccupancy(modelMapper.map(room.getOccupancy(), OccupancyVo.class));
    }

    public RoomUpdateCommand toCommand(RoomUpdateRequest request) {
        return RoomUpdateCommand.builder()
            .stock(modelMapper.map(request.getStock(), RoomStock.class))
            .basic(modelMapper.map(request.getBasic(), RoomBasic.class))
            .occupancy(modelMapper.map(request.getOccupancy(), Occupancy.class))
            .images(request.getImages())
            .build();
    }

    public RoomCreateCommand toCommand(RoomCreateRequest request) {
        return RoomCreateCommand.builder()
            .stock(modelMapper.map(request.getStock(), RoomStock.class))
            .basic(modelMapper.map(request.getBasic(), RoomBasic.class))
            .occupancy(modelMapper.map(request.getOccupancy(), Occupancy.class))
            .images(request.getImages())
            .build();
    }
}
