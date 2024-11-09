package com.eleven.hotel.application.service;

import com.eleven.core.application.query.PageResult;
import com.eleven.core.domain.DomainContext;
import com.eleven.hotel.api.application.error.HotelErrors;
import com.eleven.hotel.api.application.event.PlanCreatedEvent;
import com.eleven.hotel.application.command.*;
import com.eleven.hotel.application.support.HotelContext;
import com.eleven.hotel.domain.model.plan.InventoryManager;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.domain.model.hotel.RoomRepository;
import com.eleven.hotel.domain.model.plan.*;
import com.eleven.hotel.domain.values.StockAmount;
import com.github.wenhao.jpa.Specifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;

    private final InventoryManager inventoryManager;

    public Optional<Plan> readPlan(Long hotelId, Long planId) {
        return planRepository.findByKey(PlanKey.of(hotelId, planId)).filter(HotelContext::mustReadable);
    }

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

    @Transactional(rollbackFor = Exception.class)
    public Plan createPlan(Long hotelId, PlanCreateCommand command) {
        // verify the hotel is existing
        var hotel = hotelRepository.findById(hotelId).orElseThrow(HotelContext::noPrincipalException);

        // create the plan
        var plan = Plan.normal()
                .hotelId(hotel.getHotelId())
                .salePeriod(command.getSalePeriod())
                .preSellPeriod(command.getPreSalePeriod())
                .stayPeriod(command.getStayPeriod())
                .basic(command.getBasic())
                .stockAmount(command.getStock())
                .saleChannels(command.getChannels())
                .create();

        // add rooms to the plan
        if (CollectionUtils.isNotEmpty(command.getRooms())) {
            var rooms = roomRepository.findAllById(command.getRooms());
            for (Room room : rooms) {
                plan.addRoom(room.getRoomId(), StockAmount.of(100));
            }
        }

        // persist the plan
        planRepository.persistAndFlush(plan);

        // initialize the inventory
        inventoryManager.mergeInventory(plan);

        // publish application event inside the transaction
        HotelContext.publishEvent(PlanCreatedEvent.of(plan.getHotelId(), plan.getPlanId()));

        // return back the created plan
        return plan;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updatePlan(Long hotelId, Long planId, PlanUpdateCommand command) {
        var plan = planRepository.findByKey(PlanKey.of(hotelId, planId)).orElseThrow(HotelContext::noPrincipalException);

        Optional.ofNullable(command.getBasic()).ifPresent(plan::setBasic);
        Optional.ofNullable(command.getStock()).ifPresent(plan::setStock);
        Optional.ofNullable(command.getStayPeriod()).ifPresent(plan::setStayPeriod);
        Optional.ofNullable(command.getSalePeriod()).ifPresent(plan::setSalePeriod);
        Optional.ofNullable(command.getPreSalePeriod()).ifPresent(plan::setPreSalePeriod);
        Optional.ofNullable(command.getChannels()).ifPresent(plan::setSaleChannels);

        planRepository.updateAndFlush(plan);

        inventoryManager.mergeInventory(plan);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletePlan(Long hotelId, Long planId) {
        var plan = planRepository.findByKey(PlanKey.of(hotelId, planId)).orElseThrow(HotelContext::noPrincipalException);

        // delete the plan
        planRepository.delete(plan);

        // delete inventories
        inventoryRepository.deleteByPlanKey(plan.toPlanKey());
    }

    @Transactional(rollbackFor = Exception.class)
    public void startSale(Long hotelId, Long planId, Long roomId) {
        var plan = planRepository.findByKey(PlanKey.of(hotelId, planId)).orElseThrow(HotelContext::noPrincipalException);
        DomainContext.must(plan.hasRoom(roomId), HotelErrors.PLAN_NO_SUCH_ROOM);
        DomainContext.must(plan.hasStock(roomId), HotelErrors.PLAN_PRODUCT_NO_STOCK);
        DomainContext.must(plan.hasPrice(roomId), HotelErrors.PLAN_PRODUCT_NO_PRICE);
        plan.startSale(roomId);
        planRepository.updateAndFlush(plan);
    }

    @Transactional(rollbackFor = Exception.class)
    public void stopSale(Long hotelId, Long planId, Long roomId) {
        var plan = planRepository.findByKey(PlanKey.of(hotelId, planId)).orElseThrow(HotelContext::noPrincipalException);
        DomainContext.must(plan.hasRoom(roomId), HotelErrors.PLAN_NO_SUCH_ROOM);
        plan.stopSale(roomId);
        planRepository.updateAndFlush(plan);
    }

    @Transactional(rollbackFor = Exception.class)
    public void setPrice(Long hotelId, Long planId, Long roomId, PlanSetPriceCommand command) {
        var plan = planRepository.findByKey(PlanKey.of(hotelId, planId)).orElseThrow(HotelContext::noPrincipalException);
        DomainContext.must(plan.hasRoom(roomId), HotelErrors.PLAN_NO_SUCH_ROOM);
        DomainContext.must(plan.hasStock(roomId), HotelErrors.PLAN_PRODUCT_NO_STOCK);

        switch (command.getChargeType()) {
            case BY_PERSON -> plan.setPrice(
                    roomId,
                    command.getSaleChannel(),
                    command.getOnePersonPrice(),
                    command.getTwoPersonPrice(),
                    command.getThreePersonPrice(),
                    command.getFourPersonPrice(),
                    command.getFivePersonPrice()
            );
            case BY_ROOM -> plan.setPrice(
                    roomId,
                    command.getSaleChannel(),
                    command.getWholeRoomPrice()
            );
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void reduceStock(Long hotelId, Long planId, Long roomId, StockReduceCommand command) {
        var productId = ProductId.of(hotelId, planId, roomId);
        var inventoryId = InventoryKey.of(productId, command.getDate());
        var inventory = inventoryRepository.findByKey(inventoryId).orElseThrow(HotelContext::noPrincipalException);
        DomainContext.must(inventory.hasEnoughStock(command.getAmount()), HotelErrors.PLAN_INVENTORY_NOT_ENOUGH);
        inventory.reduce(command.getAmount());
        inventoryRepository.updateAndFlush(inventory);
    }

}
