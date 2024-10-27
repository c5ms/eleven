package com.eleven.hotel.endpoint.convert;

import com.eleven.hotel.api.endpoint.model.HotelDto;
import com.eleven.hotel.api.endpoint.model.RegisterDto;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.hotel.Register;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelConvertor {

    public final ValuesConvertor values;

    public RegisterDto toDto(Register register) {
        return new RegisterDto()
            .setRegisterID(register.getRegisterId())
            .setHotelName(register.getHotel().getName())
            .setHotelAddress(register.getHotel().getAddress())
            .setAdminName(register.getAdmin().getName())
            .setAdminEmail(register.getAdmin().getEmail())
            .setAdminTel(register.getAdmin().getTel())
            .setState(register.getState());
    }

    public HotelDto toDto(Hotel hotel) {
        return new HotelDto()
            .setHotelId(hotel.getHotelId())
            .setState(hotel.getSaleState())

            .setName(hotel.getDescription().getName())
            .setDescription(hotel.getDescription().getDescription())
            .setRoomNumber(hotel.getDescription().getRoomNumber())
            .setHeadPicUrl(hotel.getDescription().getHeadPicUrl())
            .setCheckIn(hotel.getDescription().getCheckInTime())
            .setCheckOut(hotel.getDescription().getCheckOutTime())

            .setTel(hotel.getDescription().getTel())
            .setEmail(hotel.getDescription().getEmail())

            .setProvince(hotel.getPosition().getProvince())
            .setCity(hotel.getPosition().getCity())
            .setDistrict(hotel.getPosition().getDistrict())
            .setStreet(hotel.getPosition().getStreet())
            .setAddress(hotel.getPosition().getAddress())
            .setLat(hotel.getPosition().getLat())
            .setLng(hotel.getPosition().getLng())
            ;
    }


}
