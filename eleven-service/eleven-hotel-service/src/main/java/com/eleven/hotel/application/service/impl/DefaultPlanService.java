package com.eleven.hotel.application.service.impl;

import com.eleven.hotel.application.command.PlanAddRoomCommand;
import com.eleven.hotel.application.command.PlanCreateCommand;
import com.eleven.hotel.application.service.PlanService;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.model.hotel.HotelRoomRepository;
import com.eleven.hotel.domain.model.plan.Plan;
import com.eleven.hotel.domain.model.plan.PlanDesc;
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
class DefaultPlanService implements PlanService {

    private final PlanManager planManager;
    private final PlanRepository planRepository;
    private final HotelRepository hotelRepository;
    private final HotelRoomRepository hotelRoomRepository;

    @Override
    public Plan createPlan(PlanCreateCommand command) {
        var plan = Plan.createPlan(
                planManager.planId(),
                hotelRepository.requireById(command.getHotelId()),
                command.getName(),
                command.getStock(),
                command.getSellPeriod(),
                command.getStayPeriod(),
                PlanDesc.builder()
                        .desc(command.getDesc())
                        .build()
        );
        planManager.validate(plan);
        planRepository.save(plan);
        return plan;
    }

    @Override
    public void addRoom(PlanAddRoomCommand command) {
        var plan = planRepository.requireById(command.getPlanId());
        var room = hotelRoomRepository.requireById(command.getRoomId());
        var stock = command.getStock();
        var price = command.getPrice();
        plan.addRoom(room, stock, price);
        planRepository.save(plan);
    }


}
