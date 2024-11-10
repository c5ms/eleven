package com.eleven.hotel.interfaces.convert;

import com.eleven.hotel.api.interfaces.model.HotelDto;
import com.eleven.hotel.api.interfaces.model.RegisterDto;
import com.eleven.hotel.api.interfaces.request.HotelCreateRequest;
import com.eleven.hotel.api.interfaces.request.HotelQueryRequest;
import com.eleven.hotel.api.interfaces.request.HotelRegisterRequest;
import com.eleven.hotel.api.interfaces.request.HotelUpdateRequest;
import com.eleven.hotel.application.command.HotelCreateCommand;
import com.eleven.hotel.application.command.HotelRegisterCommand;
import com.eleven.hotel.application.command.HotelUpdateCommand;
import com.eleven.hotel.application.command.HotelQuery;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.hotel.HotelBasic;
import com.eleven.hotel.domain.model.hotel.HotelPosition;
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

            .setName(hotel.getBasic().getName())
            .setDescription(hotel.getBasic().getDescription())
            .setTotalRooms(hotel.getBasic().getTotalRooms())
            .setHeadPicUrl(hotel.getBasic().getHeadPicUrl())
            .setCheckIn(hotel.getBasic().getCheckInTime())
            .setCheckOut(hotel.getBasic().getCheckOutTime())

            .setTel(hotel.getBasic().getTel())
            .setEmail(hotel.getBasic().getEmail())

            .setProvince(hotel.getPosition().getProvince())
            .setCity(hotel.getPosition().getCity())
            .setDistrict(hotel.getPosition().getDistrict())
            .setStreet(hotel.getPosition().getStreet())
            .setAddress(hotel.getPosition().getAddress())
            .setLat(hotel.getPosition().getLat())
            .setLng(hotel.getPosition().getLng())
            ;
    }

    public HotelCreateCommand toCommand(HotelCreateRequest request) {
        return HotelCreateCommand.builder()
            .basic(new HotelBasic(
                request.getName(),
                request.getDescription(),
                request.getHeadPicUrl(),
                request.getTotalRooms(),
                request.getCheckIn(),
                request.getCheckOut(),
                request.getEmail(),
                request.getTel())
            )
            .position(new HotelPosition(
                request.getProvince(),
                request.getCity(),
                request.getDistrict(),
                request.getStreet(),
                request.getAddress(),
                request.getLat(),
                request.getLng()
            ))
            .build();
    }

    public HotelUpdateCommand toCommand(HotelUpdateRequest request) {
        return HotelUpdateCommand.builder()
            .basic(new HotelBasic(
                request.getName(),
                request.getDescription(),
                request.getHeadPicUrl(),
                request.getTotalRooms(),
                request.getCheckIn(),
                request.getCheckOut(),
                request.getEmail(),
                request.getTel())
            )
            .position(new HotelPosition(
                request.getProvince(),
                request.getCity(),
                request.getDistrict(),
                request.getStreet(),
                request.getAddress(),
                request.getLat(),
                request.getLng()
            ))
            .build();
    }

    public HotelQuery toCommand(HotelQueryRequest request) {
        return HotelQuery.builder()
            .hotelName(request.getHotelName())
            .build();
    }

    public HotelRegisterCommand toCommand(HotelRegisterRequest request) {
        return HotelRegisterCommand.builder()
            .admin(new Register.AdminInformation(request.getAdminName(), request.getAdminEmail(), request.getAdminTel()))
            .hotel(new Register.HotelInformation(request.getHotelName(), request.getHotelAddress()))
            .build();
    }
}
