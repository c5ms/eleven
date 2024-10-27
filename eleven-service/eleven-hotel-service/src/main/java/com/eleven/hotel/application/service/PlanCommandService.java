package com.eleven.hotel.application.service;

import com.eleven.hotel.api.domain.core.HotelErrors;
import com.eleven.hotel.application.command.PlanAddRoomCommand;
import com.eleven.hotel.application.command.PlanCreateCommand;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.model.hotel.RoomRepository;
import com.eleven.hotel.domain.model.plan.Plan;
import com.eleven.hotel.domain.model.plan.PlanManager;
import com.eleven.hotel.domain.model.plan.PlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class PlanCommandService {

    private final PlanManager planManager;
    private final PlanRepository planRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    public Plan createPlan(PlanCreateCommand command) {
        var hotel = hotelRepository.require(command.getHotelId());
        var plan = planManager.create(hotel,
            command.getSellPeriod(),
            command.getPreSellPeriod(),
            command.getStayPeriod(),
            command.getDescription(),
            command.getStock()
        );
        planManager.validate(plan);
        planRepository.save(plan);
        return plan;
    }

    public void addRoom(PlanAddRoomCommand command) {
        var plan = planRepository.require(command.getHotelId(), command.getPlanId());
        //todo how to throw
        var room = roomRepository.require(command.getHotelId(), command.getRoomId());
        var stock = command.getStock();
        var price = command.getPrice();
        plan.addRoom(room, stock, price);
        planRepository.save(plan);
    }


}
