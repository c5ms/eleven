package com.eleven.hotel.interfaces.converter;

import com.eleven.hotel.api.interfaces.vo.RoomBasicVo;
import com.eleven.hotel.domain.model.hotel.RoomBasic;
import com.eleven.hotel.domain.model.hotel.values.Occupancy;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class RoomConverter {

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
