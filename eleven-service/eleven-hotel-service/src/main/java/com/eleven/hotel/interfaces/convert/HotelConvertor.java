package com.eleven.hotel.interfaces.convert;

import com.eleven.hotel.api.application.constants.HotelConstants;
import com.eleven.hotel.api.interfaces.dto.HotelDto;
import com.eleven.hotel.api.interfaces.request.HotelCreateRequest;
import com.eleven.hotel.api.interfaces.request.HotelUpdateRequest;
import com.eleven.hotel.application.command.HotelCreateCommand;
import com.eleven.hotel.application.command.HotelUpdateCommand;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.hotel.HotelBasic;
import com.eleven.hotel.domain.model.hotel.values.Address;
import com.eleven.hotel.domain.model.hotel.values.CheckPolicy;
import com.eleven.hotel.domain.model.hotel.values.Position;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelConvertor {

    public HotelDto toDto(Hotel hotel) {
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

    public HotelCreateCommand toCommand(HotelCreateRequest request) {
        return HotelCreateCommand.builder()
                .basic(new HotelBasic(
                        request.getName(),
                        request.getDescription(),
                        request.getEmail(),
                        request.getPhone(),
                        request.getTotalRoomQuantity(),
                        request.getWhenBuilt(),
                        request.getLastRenovation(),
                        request.getStarRating(),
                        request.getBuildingArea()
                ))
                .position(new Position(
                        request.getLatitude(),
                        request.getLongitude()
                ))
                .address(new Address(
                        request.getCountry(),
                        request.getProvince(),
                        request.getCity(),
                        request.getLocation(),
                        request.getAddress()
                ))
                .checkPolicy(new CheckPolicy(
                        LocalTime.parse(request.getCheckIn(), HotelConstants.FORMATTER_HOUR_MINUTES),
                        LocalTime.parse(request.getCheckOut(), HotelConstants.FORMATTER_HOUR_MINUTES)
                ))
                .build();
    }


    public HotelUpdateCommand toCommand(HotelUpdateRequest request) {
        return HotelUpdateCommand.builder()
                .basic(new HotelBasic(
                        request.getName(),
                        request.getDescription(),
                        request.getEmail(),
                        request.getPhone(),
                        request.getTotalRoomQuantity(),
                        request.getWhenBuilt(),
                        request.getLastRenovation(),
                        request.getStarRating(),
                        request.getBuildingArea()
                ))
                .position(new Position(
                        request.getLatitude(),
                        request.getLongitude()
                ))
                .address(new Address(
                        request.getCountry(),
                        request.getProvince(),
                        request.getCity(),
                        request.getLocation(),
                        request.getAddress()
                ))
                .checkPolicy(new CheckPolicy(
                        request.getCheckIn(),
                        request.getCheckOut()
                ))
                .build();

    }
}
