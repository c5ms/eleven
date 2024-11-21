package com.eleven.hotel.interfaces.converter;

import com.eleven.hotel.api.interfaces.model.hotel.HotelCreateRequest;
import com.eleven.hotel.api.interfaces.model.hotel.HotelDto;
import com.eleven.hotel.api.interfaces.model.hotel.HotelUpdateRequest;
import com.eleven.hotel.api.interfaces.values.AddressVo;
import com.eleven.hotel.api.interfaces.values.CheckPolicyVo;
import com.eleven.hotel.api.interfaces.values.HotelBasicVo;
import com.eleven.hotel.api.interfaces.values.PositionVo;
import com.eleven.hotel.application.command.HotelCreateCommand;
import com.eleven.hotel.application.command.HotelUpdateCommand;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.values.Address;
import com.eleven.hotel.domain.values.CheckPolicy;
import com.eleven.hotel.domain.values.HotelBasic;
import com.eleven.hotel.domain.values.Position;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


@Component
@RequiredArgsConstructor
public class HotelConvertor implements InitializingBean {

    private final ModelMapper modelMapper;

    @Override
    public void afterPropertiesSet() {
        modelMapper.createTypeMap(CheckPolicyVo.class, CheckPolicy.class).setConverter(context -> CheckPolicy.of(
                LocalTime.parse(context.getSource().getCheckInTime(), DateTimeFormatter.ofPattern(CheckPolicyVo.FORMAT_HH_MM)),
                LocalTime.parse(context.getSource().getCheckOutTime(), DateTimeFormatter.ofPattern(CheckPolicyVo.FORMAT_HH_MM))
        ));
    }

    public HotelDto toDto(Hotel hotel) {
        return new HotelDto()
                .setHotelId(hotel.getHotelId())
                .setActive(hotel.getActive())
                .setBasic(modelMapper.map(hotel.getBasic(), HotelBasicVo.class))
                .setAddress(modelMapper.map(hotel.getAddress(), AddressVo.class))
                .setPosition(modelMapper.map(hotel.getPosition(), PositionVo.class))
                .setCheckPolicy(modelMapper.map(hotel.getCheckPolicy(), CheckPolicyVo.class));
    }

    public HotelUpdateCommand toCommand(HotelUpdateRequest request) {
        return HotelUpdateCommand.builder()
                .basic(modelMapper.map(request.getBasic(), HotelBasic.class))
                .address(modelMapper.map(request.getAddress(), Address.class))
                .position(modelMapper.map(request.getPosition(), Position.class))
                .checkPolicy(modelMapper.map(request.getCheckPolicy(), CheckPolicy.class))
                .build();
    }

    public HotelCreateCommand toCommand(HotelCreateRequest request) {
        return HotelCreateCommand.builder()
                .basic(modelMapper.map(request.getBasic(), HotelBasic.class))
                .address(modelMapper.map(request.getAddress(), Address.class))
                .position(modelMapper.map(request.getPosition(), Position.class))
                .checkPolicy(modelMapper.map(request.getCheckPolicy(), CheckPolicy.class))
                .build();
    }


}
