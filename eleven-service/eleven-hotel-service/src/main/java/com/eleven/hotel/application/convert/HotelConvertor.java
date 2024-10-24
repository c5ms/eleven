package com.eleven.hotel.application.convert;

import com.eleven.hotel.api.application.view.HotelDto;
import com.eleven.hotel.api.application.view.PlanDto;
import com.eleven.hotel.api.application.view.RegisterDto;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.hotel.Register;
import com.eleven.hotel.domain.model.plan.Plan;
import com.eleven.hotel.domain.model.plan.PlanRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelConvertor {

    public final ValuesConvertor values;
    public final Entities entities = new Entities();

    private final ModelMapper modelMapper;

    public class Entities {
        public HotelDto toDto(Hotel hotel) {
            return new HotelDto()
                .setId(hotel.getId())
                .setState(hotel.getSaleState())
                .setName(hotel.getName())

                .setDescription(hotel.getDesc().getDescription())
                .setRoomNumber(hotel.getDesc().getRoomNumber())
                .setHeadPicUrl(hotel.getDesc().getHeadPicUrl())
                .setCheckIn(hotel.getDesc().getCheckInTime())
                .setCheckOut(hotel.getDesc().getCheckOutTime())

                .setTel(hotel.getContact().getTel())
                .setEmail(hotel.getContact().getEmail())

                .setProvince(hotel.getPosition().getProvince())
                .setCity(hotel.getPosition().getCity())
                .setDistrict(hotel.getPosition().getDistrict())
                .setStreet(hotel.getPosition().getStreet())
                .setAddress(hotel.getPosition().getAddress())
                .setLat(hotel.getPosition().getLat())
                .setLng(hotel.getPosition().getLng())
                ;
        }

        public RegisterDto toDto(Register register) {
            return new RegisterDto()
                .setId(register.getId())
                .setHotelName(register.getHotelName())
                .setHotelAddress(register.getHotelAddress())
                .setAdminName(register.getManagerContact().getName())
                .setAdminEmail(register.getManagerContact().getEmail())
                .setAdminTel(register.getManagerContact().getTel())
                .setState(register.getState());
        }

        public PlanDto toDto(Plan plan) {
            return new PlanDto()
                .setId(plan.getId())
                .setName(plan.getName())
                .setDesc(plan.getDesc().getDesc())
                .setCount(plan.getStock().getCount())
                .setType(plan.getSaleType())
                .setState(plan.getSaleState())

                .setPreSellStartDate(plan.getPreSalePeriod().getStart())
                .setPreSellEndDate(plan.getPreSalePeriod().getEnd())

                .setSellStartDate(plan.getSalePeriod().getStart())
                .setSellEndDate(plan.getSalePeriod().getEnd())

                .setStayStartDate(plan.getStayPeriod().getStart())
                .setStayEndDate(plan.getStayPeriod().getEnd())

                .setRooms(plan.getRooms().stream().map(this::toDto).collect(Collectors.toList()))
                ;
        }

        private PlanDto.PlanRoom toDto(PlanRoom planRoom) {
            return new PlanDto.PlanRoom()
                .setRoomId(planRoom.getRoomId())
                .setStock(planRoom.getStock().getCount())
                .setPrice(planRoom.getPrice().getAmount().doubleValue());
        }

    }



}
