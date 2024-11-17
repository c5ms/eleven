package com.eleven.hotel.interfaces.assembler;

import com.eleven.hotel.api.interfaces.dto.HotelDto;
import com.eleven.hotel.api.interfaces.dto.RoomDto;
import com.eleven.hotel.api.interfaces.vo.DateRangeVo;
import com.eleven.hotel.api.interfaces.vo.RoomBasicVo;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.hotel.Room;
import org.springframework.stereotype.Component;

@Component
public class HotelAssembler {

    public HotelDto assembleDto(Hotel hotel) {
        return new HotelDto()
            .setHotelId(hotel.getHotelId())
            .setActive(hotel.getActive())
            .setName(hotel.getBasic().getName())
            .setDescription(hotel.getBasic().getDescription())
            .setTotalRoomQuantity(hotel.getBasic().getTotalRoomQuantity())
            .setCheckIn(hotel.getCheckPolicy().getCheckInTime())
            .setCheckOut(hotel.getCheckPolicy().getCheckOutTime())

            .setPhone(hotel.getBasic().getPhone())
            .setEmail(hotel.getBasic().getEmail())

            .setCountry(hotel.getAddress().getCountry())
            .setProvince(hotel.getAddress().getProvince())
            .setCity(hotel.getAddress().getCity())
            .setLocation(hotel.getAddress().getLocation())
            .setAddress(hotel.getAddress().getAddress())

            .setLongitude(hotel.getPosition().getLatitude())
            .setLongitude(hotel.getPosition().getLongitude())
            ;
    }
}
