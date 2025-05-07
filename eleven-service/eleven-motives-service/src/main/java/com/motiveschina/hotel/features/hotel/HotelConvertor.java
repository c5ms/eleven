package com.motiveschina.hotel.features.hotel;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import com.motiveschina.hotel.features.hotel.command.HotelCreateCommand;
import com.motiveschina.hotel.features.hotel.command.HotelUpdateCommand;
import com.motiveschina.hotel.features.hotel.request.HotelCreateRequest;
import com.motiveschina.hotel.features.hotel.request.HotelUpdateRequest;
import com.motiveschina.hotel.features.hotel.values.Address;
import com.motiveschina.hotel.features.hotel.values.CheckPolicy;
import com.motiveschina.hotel.features.hotel.values.HotelBasic;
import com.motiveschina.hotel.features.hotel.values.Position;
import com.motiveschina.hotel.features.hotel.vo.AddressVo;
import com.motiveschina.hotel.features.hotel.vo.CheckPolicyVo;
import com.motiveschina.hotel.features.hotel.vo.HotelBasicVo;
import com.motiveschina.hotel.features.hotel.vo.PositionVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;


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
