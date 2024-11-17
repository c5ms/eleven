package com.eleven.hotel.interfaces.assembler;

import com.eleven.hotel.api.interfaces.dto.RoomDto;
import com.eleven.hotel.api.interfaces.vo.DateRangeVo;
import com.eleven.hotel.api.interfaces.vo.RoomBasicVo;
import com.eleven.hotel.domain.model.hotel.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomAssembler {

    public RoomDto assembleDto(Room room) {
        return new RoomDto()
            .setRoomId(room.getRoomId())
            .setQuantity(room.getQuantity())
            .setImages(room.getImages())
            .setAvailablePeriod(DateRangeVo.from(room.getAvailablePeriod()))
            .setBasic(new RoomBasicVo()
                .setName(room.getBasic().getName())
                .setArea(room.getBasic().getArea())
                .setDesc(room.getBasic().getDesc())
                .setFloor(room.getBasic().getFloor())
                .setMinPerson(room.getBasic().getOccupancy().getMinPerson())
                .setMaxPerson(room.getBasic().getOccupancy().getMaxPerson()));
    }
}
