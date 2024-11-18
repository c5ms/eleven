package com.eleven.hotel.interfaces.assembler;

import com.eleven.hotel.api.interfaces.dto.RoomDto;
import com.eleven.hotel.api.interfaces.vo.DateRangeVo;
import com.eleven.hotel.api.interfaces.vo.HotelBasicVo;
import com.eleven.hotel.domain.model.hotel.HotelBasic;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.interfaces.converter.RoomConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomAssembler {
    private final ModelMapper modelMapper;

    public RoomDto assembleDto(Room room) {
        return new RoomDto()
            .setRoomId(room.getRoomId())
            .setQuantity(room.getQuantity())
            .setImages(room.getImages())
            .setAvailablePeriod(modelMapper.map(room.getAvailablePeriod(), DateRangeVo.class))
            .setBasic(RoomConverter.toRoomBasicVo(room.getBasic()));
    }

}
