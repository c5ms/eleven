package com.eleven.hotel.application.service;

import com.eleven.core.application.query.PageResult;
import com.eleven.core.data.Audition;
import com.eleven.core.data.QuerySupport;
import com.eleven.hotel.application.command.PlanAddRoomCommand;
import com.eleven.hotel.application.command.PlanCreateCommand;
import com.eleven.hotel.application.query.PlanQuery;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.model.hotel.RoomRepository;
import com.eleven.hotel.domain.model.hotel.Plan;
import com.eleven.hotel.domain.manager.PlanManager;
import com.eleven.hotel.domain.model.hotel.PlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.relational.core.query.Criteria.empty;
import static org.springframework.data.relational.core.query.Criteria.where;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class PlanService {

    private final PlanManager planManager;
    private final PlanRepository planRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final QuerySupport querySupport;

    @Transactional(readOnly = true)
    public PageResult<Plan> queryPage(PlanQuery query) {
        var criteria = empty();

        criteria = criteria.and(where(Plan.Fields.hotelId).is(query.getHotelId()));

        if (StringUtils.isNotBlank(query.getPlanName())) {
            criteria = criteria.and(where(String.join(Plan.Fields.description, Plan.Description.Fields.name)).like(query.getPlanName()+"%"));
        }

        var sort = Sort.by(String.join(Plan.Fields.audition, Audition.Fields.createAt)).descending();
        return querySupport.query(Plan.class, criteria,  query,sort);
    }

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
        var room = roomRepository.require(command.getHotelId(), command.getRoomId());
        var stock = command.getStock();
        var price = command.getPrice();
        plan.addRoom(room, stock, price);
        planRepository.save(plan);
    }


}
