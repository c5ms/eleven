package com.eleven.hotel.application.service;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.eleven.core.application.query.PageResult;
import com.eleven.hotel.application.command.PlanAddRoomCommand;
import com.eleven.hotel.application.command.PlanCreateCommand;
import com.eleven.hotel.application.query.PlanQuery;
import com.eleven.hotel.application.support.HotelContext;
import com.eleven.hotel.domain.manager.PlanManager;
import com.eleven.hotel.domain.model.hotel.*;
import com.github.wenhao.jpa.Specifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanManager planManager;
    private final PlanRepository planRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    public Optional<Plan> readPlan(Integer hotelId, Integer planId) {
        return planRepository.findByHotelIdAndId(hotelId, planId)
                .filter(HotelContext::mustReadable);
    }

    @Transactional(readOnly = true)
    public PageResult<Plan> queryPage(Integer hotelId, PlanQuery query) {
        hotelRepository.findById(hotelId).orElseThrow(HotelContext::noPrincipalException);

        Specification<Plan> specification = Specifications.<Plan>and()
                .like(StringUtils.isNotBlank(query.getPlanName()), Plan.Fields.basic +"."+ Plan.PlanBasic.Fields.name, "%"+query.getPlanName()+"%")
                .build();

        var result = planRepository.findAll(specification, PageRequest.of(query.getPage(), query.getSize() - 1));
        return PageResult.of(result.getContent(), result.getTotalElements());
    }

    @Transactional(rollbackFor = Exception.class)
    public Plan createPlan(Integer hotelId, PlanCreateCommand command) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(HotelContext::noPrincipalException);
        var plan = Plan.normal()
                .hotelId(hotel.getId())
                .salePeriod(command.getSellPeriod())
                .preSellPeriod(command.getPreSellPeriod())
                .stayPeriod(command.getStayPeriod())
                .basic(command.getBasic())
                .stock(command.getStock())
                .create();
        planManager.validate(plan);
        planRepository.save(plan);
        return plan;
    }

    @Transactional(rollbackFor = Exception.class)
    public void addRoom(Integer hotelId, Integer planId, PlanAddRoomCommand command) {
        var plan = planRepository.findByHotelIdAndId(hotelId, planId).orElseThrow(HotelContext::noPrincipalException);
        var room = roomRepository.findByHotelIdAndId(hotelId, command.getRoomId()).orElseThrow(HotelContext::noEntityException);
        var stock = command.getStock();
        var price = command.getPrice();
        plan.addRoom(room, stock, price);
        planRepository.save(plan);
    }


}
