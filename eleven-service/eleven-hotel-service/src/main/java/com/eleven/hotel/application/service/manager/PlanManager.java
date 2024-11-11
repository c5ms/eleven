package com.eleven.hotel.application.service.manager;

import com.eleven.hotel.api.domain.errors.HotelErrors;
import com.eleven.hotel.application.command.PlanCreateCommand;
import com.eleven.hotel.application.command.PlanSetPriceCommand;
import com.eleven.hotel.application.command.PlanUpdateCommand;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.hotel.RoomRepository;
import com.eleven.hotel.domain.model.plan.Plan;
import com.eleven.hotel.domain.model.plan.PlanValidator;
import com.eleven.hotel.domain.model.plan.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Slf4j
@Component
@RequiredArgsConstructor
public class PlanManager {

    private final List<PlanValidator> planValidators;
    private final RoomRepository roomRepository;

    public void validate(Plan plan) {
        for (PlanValidator validator : planValidators) {
            validator.validate(plan);
        }
    }

    public Plan createPlan(Hotel hotel, PlanCreateCommand command) {
        var plan = Plan.normal()
            .hotelId(hotel.getHotelId())
            .salePeriod(command.getSalePeriod())
            .preSellPeriod(command.getPreSalePeriod())
            .stayPeriod(command.getStayPeriod())
            .basic(command.getBasic())
            .stockAmount(command.getStock())
            .saleChannels(command.getChannels())
            .create();

        for (Long roomId : command.getRooms()) {
            var room = roomRepository.findByHotelIdAndRoomId(plan.getHotelId(), roomId).orElseThrow(HotelErrors.ROOM_NOT_FOUND::toException);
            plan.addRoom(room.getRoomId());
        }

        return plan;
    }

    public void update(Plan plan, PlanUpdateCommand command) {
        Optional.ofNullable(command.getBasic()).ifPresent(plan::setBasic);
        Optional.ofNullable(command.getStock()).ifPresent(plan::setStock);
        Optional.ofNullable(command.getStayPeriod()).ifPresent(plan::setStayPeriod);
        Optional.ofNullable(command.getSalePeriod()).ifPresent(plan::setSalePeriod);
        Optional.ofNullable(command.getPreSalePeriod()).ifPresent(plan::setPreSalePeriod);
        Optional.ofNullable(command.getChannels()).ifPresent(plan::setSaleChannels);

        command.getRooms().stream()
            .filter(roomId -> plan.findRoom(roomId).isEmpty())
            .map(roomId -> roomRepository.findByHotelIdAndRoomId(plan.getHotelId(), roomId).orElseThrow(HotelErrors.ROOM_NOT_FOUND::toException))
            .forEach(room -> plan.addRoom(room.getRoomId()));
        plan.removeRoom(product -> !command.getRooms().contains(product.getProductKey().getRoomId()));
    }

    public void setPrice(Product room, PlanSetPriceCommand command) {
        switch (command.getChargeType()) {
            case BY_PERSON -> room.setPrice(
                command.getSaleChannel(),
                command.getOnePersonPrice(),
                command.getTwoPersonPrice(),
                command.getThreePersonPrice(),
                command.getFourPersonPrice(),
                command.getFivePersonPrice()
            );
            case BY_ROOM -> room.setPrice(
                command.getSaleChannel(),
                command.getWholeRoomPrice()
            );
        }
    }

}
