package com.eleven.hotel.interfaces.converter;

import com.eleven.hotel.api.interfaces.vo.RoomBasicVo;
import com.eleven.hotel.domain.model.hotel.RoomBasic;
import com.eleven.hotel.domain.model.hotel.values.Occupancy;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@UtilityClass
public class RoomConverter {

    public static RoomBasic toRoomBasic(RoomBasicVo vo) {
        return com.eleven.hotel.domain.model.hotel.RoomBasic.builder()
            .area(vo.getArea())
            .desc(vo.getDesc())
            .floor(vo.getFloor())
            .name(vo.getName())
            .occupancy(new Occupancy(vo.getMinPerson(), vo.getMaxPerson()))
            .build();
    }

}
