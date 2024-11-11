package com.eleven.hotel.application.service.manager;

import com.eleven.hotel.api.domain.errors.HotelErrors;
import com.eleven.hotel.application.command.PlanAddRoomCommand;
import com.eleven.hotel.application.command.PlanCreateCommand;
import com.eleven.hotel.application.command.PlanSetPriceCommand;
import com.eleven.hotel.application.command.PlanUpdateCommand;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.hotel.RoomRepository;
import com.eleven.hotel.domain.model.plan.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;


@Slf4j
@Component
@RequiredArgsConstructor
public class PlanManager {

    private final List<PlanValidator> planValidators;
    private final PlanRepository planRepository;
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

        // persist for generated id is necessary
        validate(plan);
        planRepository.persist(plan);

        setRooms(plan, command.getRooms());
        return plan;
    }

    public void update(Plan plan, PlanUpdateCommand command) {
        Optional.ofNullable(command.getBasic()).ifPresent(plan::setBasic);
        Optional.ofNullable(command.getStock()).ifPresent(plan::setStock);
        Optional.ofNullable(command.getStayPeriod()).ifPresent(plan::setStayPeriod);
        Optional.ofNullable(command.getSalePeriod()).ifPresent(plan::setSalePeriod);
        Optional.ofNullable(command.getPreSalePeriod()).ifPresent(plan::setPreSalePeriod);
        Optional.ofNullable(command.getChannels()).ifPresent(plan::setSaleChannels);
        setRooms(plan, command.getRooms());
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

    public void setRooms(Plan plan, List<PlanAddRoomCommand> commands) {
        var keys = new HashSet<ProductKey>();
        for (PlanAddRoomCommand addRoomCommand : commands) {
            var room = roomRepository.findByHotelIdAndRoomId(plan.getHotelId(), addRoomCommand.getRoomId()).orElseThrow(HotelErrors.ROOM_NOT_FOUND::toException);
            plan.addRoom(room.getRoomId(), addRoomCommand.getStock());
            keys.add(plan.getProductKey(room.getRoomId()));
        }
        var isUseless = (Predicate<Product>) product -> !keys.contains(product.getProductKey());
        plan.removeRoom(isUseless);
    }


}
