package com.eleven.hotel.interfaces.converter;

import com.eleven.hotel.api.interfaces.dto.HotelDto;
import com.eleven.hotel.api.interfaces.request.HotelUpdateRequest;
import com.eleven.hotel.api.interfaces.vo.AddressVo;
import com.eleven.hotel.api.interfaces.vo.CheckPolicyVo;
import com.eleven.hotel.api.interfaces.vo.HotelBasicVo;
import com.eleven.hotel.api.interfaces.vo.PositionVo;
import com.eleven.hotel.application.command.HotelUpdateCommand;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.hotel.HotelBasic;
import com.eleven.hotel.domain.model.hotel.values.Address;
import com.eleven.hotel.domain.model.hotel.values.CheckPolicy;
import com.eleven.hotel.domain.model.hotel.values.Position;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class HotelConvertor {

    private final ModelMapper modelMapper;

    public HotelDto assembleDto(Hotel hotel) {
        return new HotelDto()
            .setHotelId(hotel.getHotelId())
            .setActive(hotel.getActive())
            .setHotelBasic(modelMapper.map(hotel.getBasic(), HotelBasicVo.class))
            .setAddress(modelMapper.map(hotel.getAddress(), AddressVo.class))
            .setPosition(modelMapper.map(hotel.getPosition(), PositionVo.class))
            .setCheckPolicy(modelMapper.map(hotel.getCheckPolicy(), CheckPolicyVo.class));
    }


    public HotelUpdateCommand toHotelUpdateCommand(HotelUpdateRequest request) {
        return HotelUpdateCommand.builder()
            .basic(toHotelBasic(request.getHotelBasic()))
            .address(toAddress(request.getAddress()))
            .position(toPosition(request.getPosition()))
            .checkPolicy(toCheckPolicy(request.getCheckPolicy()))
            .build();
    }
    public HotelBasic toHotelBasic(HotelBasicVo vo) {
        return modelMapper.map(vo, HotelBasic.class);
    }

    public Address toAddress(AddressVo vo) {
        return modelMapper.map(vo, Address.class);
    }

    public Position toPosition(PositionVo vo) {
        return modelMapper.map(vo, Position.class);
    }

    public CheckPolicy toCheckPolicy(CheckPolicyVo vo) {
        return modelMapper.map(vo, CheckPolicy.class);
    }

}
