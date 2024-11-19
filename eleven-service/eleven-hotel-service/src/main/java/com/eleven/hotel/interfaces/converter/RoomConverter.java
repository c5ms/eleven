package com.eleven.hotel.interfaces.converter;

import com.eleven.hotel.api.interfaces.model.room.RoomBasicVo;
import com.eleven.hotel.api.interfaces.model.room.RoomDto;
import com.eleven.hotel.api.interfaces.values.DateRangeVo;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.domain.model.hotel.RoomBasic;
import com.eleven.hotel.domain.values.Occupancy;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomConverter {
    private final ModelMapper modelMapper;

    public RoomDto assembleDto(Room room) {
        return new RoomDto()
            .setRoomId(room.getRoomId())
            .setQuantity(room.getQuantity())
            .setImages(room.getImages())
            .setAvailablePeriod(modelMapper.map(room.getAvailablePeriod(), DateRangeVo.class))
            .setBasic(toRoomBasicVo(room.getBasic()));
    }

    public static RoomBasic toRoomBasic(RoomBasicVo vo) {
        return RoomBasic.builder()
                .area(vo.getArea())
                .desc(vo.getDesc())
                .floor(vo.getFloor())
                .name(vo.getName())
                .occupancy(new Occupancy(vo.getMinPerson(), vo.getMaxPerson()))
                .build();
    }

    public static RoomBasicVo toRoomBasicVo(RoomBasic roomBasic) {
        return new RoomBasicVo()
                .setName(roomBasic.getName())
                .setArea(roomBasic.getArea())
                .setDesc(roomBasic.getDesc())
                .setFloor(roomBasic.getFloor())
                .setMinPerson(roomBasic.getOccupancy().getMinPerson())
                .setMaxPerson(roomBasic.getOccupancy().getMaxPerson());
    }


}
