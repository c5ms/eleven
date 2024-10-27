package com.eleven.hotel.application.service;

import com.eleven.hotel.application.command.PlanAddRoomCommand;
import com.eleven.hotel.application.command.PlanCreateCommand;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.model.plan.Plan;
import com.eleven.hotel.domain.model.plan.PlanManager;
import com.eleven.hotel.domain.model.plan.PlanRepository;
import com.eleven.hotel.domain.model.room.RoomRepository;
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
        var hotel = hotelRepository.requireById(command.getHotelId());
        var plan = Plan.normal()
            .id(planManager.nextPlanId())
            .hotelId(command.getHotelId())
            .sellPeriod(command.getSellPeriod())
            .preSellPeriod(command.getPreSellPeriod())
            .stayPeriod(command.getStayPeriod())
            .description(command.getDescription())
            .stock(command.getStock())
            .create();
        planManager.validate(plan);
        planRepository.save(plan);
        return plan;
    }

    public void addRoom(PlanAddRoomCommand command) {
        var plan = planRepository.requireById(command.getPlanId());
        var room = roomRepository.requireById(command.getRoomId());
        var stock = command.getStock();
        var price = command.getPrice();
        plan.addRoom(room, stock, price);
        planRepository.save(plan);
    }


}
