package com.eleven.travel.domain.hotel;

import com.eleven.travel.domain.hotel.command.HotelCreateCommand;
import com.eleven.travel.domain.hotel.command.HotelUpdateCommand;
import com.eleven.travel.domain.hotel.request.HotelCreateRequest;
import com.eleven.travel.domain.hotel.request.HotelUpdateRequest;
import com.eleven.travel.domain.hotel.values.Address;
import com.eleven.travel.domain.hotel.values.CheckPolicy;
import com.eleven.travel.domain.hotel.values.HotelBasic;
import com.eleven.travel.domain.hotel.values.Position;
import com.eleven.travel.domain.hotel.vo.AddressVo;
import com.eleven.travel.domain.hotel.vo.CheckPolicyVo;
import com.eleven.travel.domain.hotel.vo.HotelBasicVo;
import com.eleven.travel.domain.hotel.vo.PositionVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
        modelMapper.createTypeMap(CheckPolicyVo.class, CheckPolicy.class).setConverter(context -> {
            if (StringUtils.isAnyBlank(context.getSource().getCheckInTime(), context.getSource().getCheckOutTime())) {
                return CheckPolicy.empty();
            }
            return CheckPolicy.of(
                LocalTime.parse(context.getSource().getCheckInTime(), DateTimeFormatter.ofPattern(CheckPolicyVo.FORMAT_HH_MM)),
                LocalTime.parse(context.getSource().getCheckOutTime(), DateTimeFormatter.ofPattern(CheckPolicyVo.FORMAT_HH_MM))
            );
        });
    }

    public HotelDto toDto(Hotel hotel) {
        return null == hotel ? null : new HotelDto()
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
