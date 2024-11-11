package com.eleven.hotel.application.service;

import com.eleven.core.application.query.PageResult;
import com.eleven.hotel.api.application.event.PlanCreatedEvent;
import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.application.command.*;
import com.eleven.hotel.application.service.manager.InventoryManager;
import com.eleven.hotel.application.service.manager.PlanManager;
import com.eleven.hotel.application.support.HotelContext;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.model.plan.*;
import com.github.wenhao.jpa.Specifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;
    private final HotelRepository hotelRepository;
    private final InventoryRepository inventoryRepository;

    private final PlanManager planManager;
    private final InventoryManager invManager;

    @Transactional(readOnly = true)
    public PageResult<Plan> queryPage(Long hotelId, PlanQuery query, Pageable pageable) {
        hotelRepository.findById(hotelId).orElseThrow(HotelContext::noPrincipalException);
        Specification<Plan> specification = Specifications.<Plan>and()
            .like(StringUtils.isNotBlank(query.getPlanName()), Plan.Fields.basic + "." + PlanBasic.Fields.name, "%" + query.getPlanName() + "%")
            .build();
        var pagination = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()).withSort(Sort.by(Plan.Fields.planId).descending());
        var result = planRepository.findAll(specification, pagination);
        return PageResult.of(result.getContent(), result.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Optional<Plan> readPlan(PlanKey planKey) {
        return planRepository.findByKey(planKey).filter(HotelContext::mustReadable);
    }

    @Transactional(rollbackFor = Exception.class)
    public Plan createPlan(Long hotelId, PlanCreateCommand command) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(HotelContext::noPrincipalException);
        var plan = planManager.createPlan(hotel, command);
        planRepository.persist(plan);
        invManager.initialize(plan);
        HotelContext.publishEvent(PlanCreatedEvent.of(plan.getHotelId(), plan.getPlanId()));
        return plan;
    }

    @Transactional(rollbackFor = Exception.class)
    public Plan updatePlan(PlanKey planKey, PlanUpdateCommand command) {
        var plan = planRepository.findByKey(planKey).orElseThrow(HotelContext::noPrincipalException);
        planManager.update(plan, command);
        planManager.validate(plan);
        planRepository.updateAndFlush(plan);
        invManager.initialize(plan);
        return plan;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletePlan(PlanKey planKey) {
        var plan = planRepository.findByKey(planKey).orElseThrow(HotelContext::noPrincipalException);
        planRepository.delete(plan);
        inventoryRepository.deleteByPlanKey(plan.getPlanKey());
    }

    @Transactional(rollbackFor = Exception.class)
    public void startSale(PlanKey planKey, Long roomId) {
        var plan = planRepository.findByKey(planKey).orElseThrow(HotelContext::noPrincipalException);
        plan.findRoom(roomId).orElseThrow(HotelContext::noPrincipalException);
        plan.startSale(roomId);
        planRepository.updateAndFlush(plan);
    }

    @Transactional(rollbackFor = Exception.class)
    public void stopSale(PlanKey planKey, Long roomId) {
        var plan = planRepository.findByKey(planKey).orElseThrow(HotelContext::noPrincipalException);
        plan.findRoom(roomId).orElseThrow(HotelContext::noPrincipalException);
        plan.stopSale(roomId);
        planRepository.updateAndFlush(plan);
    }

    @Transactional(rollbackFor = Exception.class)
    public void setPrice(PlanKey planKey, Long roomId, PlanSetPriceCommand command) {
        var plan = planRepository.findByKey(planKey).orElseThrow(HotelContext::noPrincipalException);
        var room = plan.findRoom(roomId).orElseThrow(HotelContext::noPrincipalException);
        planManager.setPrice(room, command);
    }

    @Transactional(rollbackFor = Exception.class)
    public void reduceStock(ProductKey productKey, StockReduceCommand command) {
        var inventoryKey = InventoryKey.of(productKey, command.getDate());
        var inventory = inventoryRepository.findByInventoryKey(inventoryKey).orElseThrow(HotelContext::noPrincipalException);
        inventory.reduce(command.getAmount());
        inventoryRepository.updateAndFlush(inventory);
    }

    @Transactional(readOnly = true)
    public BigDecimal chargeRoom(ProductKey productKey, SaleChannel saleChannel, int persons) {
        var plan = planRepository.findByKey(productKey.toPlanKey()).orElseThrow(HotelContext::noPrincipalException);
        return plan.chargeRoom(productKey.getRoomId(), saleChannel, persons);
    }

}
