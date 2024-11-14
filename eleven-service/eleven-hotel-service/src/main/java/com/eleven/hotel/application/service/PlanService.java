package com.eleven.hotel.application.service;

import com.eleven.core.application.query.PageResult;
import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.application.command.*;
import com.eleven.hotel.application.support.HotelContext;
import com.eleven.hotel.domain.errors.HotelErrors;
import com.eleven.hotel.domain.manager.InventoryManager;
import com.eleven.hotel.domain.manager.PlanManager;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.model.plan.PlanInventoryRepository;
import com.eleven.hotel.domain.model.plan.*;
import com.eleven.hotel.domain.model.hotel.RoomKey;
import com.eleven.hotel.domain.model.hotel.RoomRepository;
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
    private final PlanInventoryRepository planInventoryRepository;

    private final PlanManager planManager;
    private final RoomRepository roomRepository;
    private final InventoryManager inventoryManager;

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
        var plan = Plan.normal()
                .hotelId(hotel.getHotelId())
                .salePeriod(command.getSalePeriod())
                .preSellPeriod(command.getPreSalePeriod())
                .stayPeriod(command.getStayPeriod())
                .basic(command.getBasic())
                .stockAmount(command.getStock())
                .saleChannels(command.getChannels())
                .create();

        command.getRooms().stream()
                .map(roomId -> RoomKey.of(hotelId, roomId))
                .map(roomKey -> roomRepository.findByRoomKey(roomKey).orElseThrow(HotelErrors.ROOM_NOT_FOUND::toException))
                .forEach(plan::addRoom);

        planManager.validate(plan);
        planRepository.saveAndFlush(plan);
        inventoryManager.initializeInventoryFor(plan);
        return plan;
    }

    @Transactional(rollbackFor = Exception.class)
    public Plan updatePlan(PlanKey planKey, PlanUpdateCommand command) {
        var plan = readPlan(planKey).orElseThrow(HotelContext::noPrincipalException);
        plan.update(command.getPatch());

        command.getRooms().stream()
                .filter(roomId -> plan.findRoom(roomId).isEmpty())
                .map(roomId -> RoomKey.of(planKey.getHotelId(), roomId))
                .map(roomKey -> roomRepository.findByRoomKey(roomKey).orElseThrow(HotelErrors.ROOM_NOT_FOUND::toException))
                .forEach(plan::addRoom);
        plan.removeRoom(product -> !command.getRooms().contains(product.getKey().getRoomId()));

        planManager.validate(plan);
        planRepository.saveAndFlush(plan);
        inventoryManager.initializeInventoryFor(plan);
        return plan;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletePlan(PlanKey planKey) {
        var plan = readPlan(planKey).orElseThrow(HotelContext::noPrincipalException);
        planRepository.delete(plan);
        planInventoryRepository.deleteByPlanKey(plan.toKey());
    }

    @Transactional(rollbackFor = Exception.class)
    public void startSale(PlanKey planKey, Long roomId) {
        var plan = readPlan(planKey).orElseThrow(HotelContext::noPrincipalException);
        plan.findRoom(roomId).orElseThrow(HotelContext::noPrincipalException);
        plan.startSale(roomId);
        planRepository.saveAndFlush(plan);
    }

    @Transactional(rollbackFor = Exception.class)
    public void stopSale(PlanKey planKey, Long roomId) {
        var plan = readPlan(planKey).orElseThrow(HotelContext::noPrincipalException);
        plan.findRoom(roomId).orElseThrow(HotelContext::noPrincipalException);
        plan.stopSale(roomId);
        planRepository.saveAndFlush(plan);
    }

    @Transactional(rollbackFor = Exception.class)
    public void setPrice(PlanKey planKey, Long roomId, PlanSetPriceCommand command) {
        var plan = readPlan(planKey).orElseThrow(HotelContext::noPrincipalException);
        var room = plan.findRoom(roomId).orElseThrow(HotelContext::noPrincipalException);
        switch (command.getChargeType()) {
            case BY_PERSON ->
                    room.setPrice(command.getSaleChannel(), command.getOnePersonPrice(), command.getTwoPersonPrice(),
                            command.getThreePersonPrice(), command.getFourPersonPrice(), command.getFivePersonPrice());
            case BY_ROOM -> room.setPrice(command.getSaleChannel(), command.getWholeRoomPrice());
        }
        planRepository.saveAndFlush(plan);
    }

    @Transactional(readOnly = true)
    public BigDecimal chargeRoom(ProductKey productKey, SaleChannel saleChannel, int persons) {
        var plan = readPlan(productKey.toPlanKey()).orElseThrow(HotelContext::noPrincipalException);
        var product = plan.findRoom(productKey.getRoomId()).orElseThrow(HotelContext::noPrincipalException);
        return product.priceOf(saleChannel).map(price -> price.charge(persons)).orElse(BigDecimal.ZERO);
    }

}
