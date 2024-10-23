package com.eleven.hotel.application.convert;

import com.eleven.hotel.api.application.model.HotelDto;
import com.eleven.hotel.api.application.model.PlanDto;
import com.eleven.hotel.api.application.model.RegisterDto;
import com.eleven.hotel.api.application.model.RoomDto;
import com.eleven.hotel.api.application.model.values.ContactDto;
import com.eleven.hotel.api.application.model.values.PositionDto;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.hotel.HotelRoom;
import com.eleven.hotel.domain.model.hotel.Register;
import com.eleven.hotel.domain.model.plan.Plan;
import com.eleven.hotel.domain.model.plan.PlanRoom;
import com.eleven.hotel.domain.values.Contact;
import com.eleven.hotel.domain.values.Position;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelConvertor {

    public final Values values = new Values();
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

                .setContact(values.toDto(hotel.getContact()))
                .setTel(hotel.getContact().getTel())
                .setEmail(hotel.getContact().getEmail())

                .setPosition(values.toDto(hotel.getPosition()))
                ;
        }

        public RegisterDto toDto(Register register) {
            return new RegisterDto()
                .setId(register.getId())
                .setHotelName(register.getHotelName())
                .setHotelAddress(register.getHotelAddress())
                .setAdminName(register.getManagerName())
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

        public List<RoomDto> toDto(List<HotelRoom> hotelRooms) {
            return hotelRooms.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        }

        public RoomDto toDto(HotelRoom hotelRoom) {
            return new RoomDto()
                .setId(hotelRoom.getId())
                .setName(hotelRoom.getName())
                .setSize(hotelRoom.getSize())
                .setSaleState(hotelRoom.getSaleState())
                .setAmount(hotelRoom.getStock().getCount())
                .setDesc(hotelRoom.getDesc().getDesc())
                ;
        }

    }

    public class Values {
        // === values ===
        private ContactDto toDto(Contact contact) {
            return modelMapper.map(contact, ContactDto.class);
        }

        public PositionDto toDto(Position position) {
            return modelMapper.map(position, PositionDto.class);
        }

        public Position toValue(PositionDto position) {
            return Position.builder()
                .province(position.getProvince())
                .city(position.getCity())
                .district(position.getDistrict())
                .street(position.getStreet())
                .address(position.getAddress())
                .lat(position.getLat())
                .lng(position.getLng())
                .build();
        }

    }

}
