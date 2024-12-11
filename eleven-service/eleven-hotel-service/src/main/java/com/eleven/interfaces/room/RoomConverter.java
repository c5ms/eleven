package com.eleven.interfaces.room;

import com.eleven.domain.hotel.values.Occupancy;
import com.eleven.domain.room.Room;
import com.eleven.domain.room.RoomBasic;
import com.eleven.domain.room.RoomDto;
import com.eleven.domain.room.RoomStock;
import com.eleven.interfaces.hotel.vo.OccupancyVo;
import com.eleven.domain.room.command.RoomCreateCommand;
import com.eleven.domain.room.command.RoomUpdateCommand;
import com.eleven.interfaces.room.request.RoomCreateRequest;
import com.eleven.interfaces.room.request.RoomUpdateRequest;
import com.eleven.interfaces.room.vo.RoomBasicVo;
import com.eleven.interfaces.room.vo.RoomStockVo;
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
