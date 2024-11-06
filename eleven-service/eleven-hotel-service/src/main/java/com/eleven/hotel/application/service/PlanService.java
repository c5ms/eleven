package com.eleven.hotel.application.service;

import com.eleven.core.application.query.PageResult;
import com.eleven.core.domain.DomainContext;
import com.eleven.hotel.api.application.error.HotelErrors;
import com.eleven.hotel.api.application.event.PlanCreatedEvent;
import com.eleven.hotel.application.command.PlanAddRoomCommand;
import com.eleven.hotel.application.command.PlanCreateCommand;
import com.eleven.hotel.application.command.PlanSetPriceCommand;
import com.eleven.hotel.application.command.StockReduceCommand;
import com.eleven.hotel.application.command.PlanQuery;
import com.eleven.hotel.application.support.HotelContext;
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

    public Optional<Plan> readPlan(Long hotelId, Long planId) {
        return planRepository.findByHotelIdAndPlanId(hotelId, planId)
            .filter(HotelContext::mustReadable);
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
            .salePeriod(command.getSellPeriod())
            .preSellPeriod(command.getPreSellPeriod())
            .stayPeriod(command.getStayPeriod())
            .basic(command.getBasic())
            .stockAmount(command.getStock())
            .saleChannels(command.getChannels())
            .create();

        // add rooms to the plan
        if (CollectionUtils.isNotEmpty(command.getRooms())) {
            var rooms = roomRepository.findAllById(command.getRooms());
            for (Room room : rooms) {
                plan.addProduct(room.getRoomId(), StockAmount.of(100));
            }
        }

        // persist the plan
        planRepository.persistAndFlush(plan);

        // initialize the inventory
        var inventories = plan.createInventories();
        inventoryRepository.persistAll(inventories);

        // return back the created plan
        HotelContext.publishEvent(PlanCreatedEvent.of(plan.getHotelId(), plan.getPlanId()));
        return plan;
    }

    @Transactional(rollbackFor = Exception.class)
    public void addRoom(Long hotelId, Long planId, PlanAddRoomCommand command) {
        var plan = planRepository.findByHotelIdAndPlanId(hotelId, planId).orElseThrow(HotelContext::noPrincipalException);
        var room = roomRepository.findByHotelIdAndRoomId(hotelId, command.getRoomId()).orElseThrow(HotelContext::noEntityException);
        var stock = command.getStock();
        plan.addProduct(room.getRoomId(), stock);
        planRepository.persist(plan);
    }

    @Transactional(rollbackFor = Exception.class)
    public void startSale(Long hotelId, Long planId, Long roomId) {
        var plan = planRepository.findByHotelIdAndPlanId(hotelId, planId).orElseThrow(HotelContext::noPrincipalException);
        DomainContext.must(plan.hasProduct(roomId), HotelErrors.PLAN_NO_SUCH_ROOM);
        DomainContext.must(plan.hasStock(roomId), HotelErrors.PLAN_PRODUCT_NO_STOCK);
        plan.startSale(roomId);
        planRepository.updateAndFlush(plan);
    }

    @Transactional(rollbackFor = Exception.class)
    public void stopSale(Long hotelId, Long planId, Long roomId) {
        var plan = planRepository.findByHotelIdAndPlanId(hotelId, planId).orElseThrow(HotelContext::noPrincipalException);
        DomainContext.must(plan.hasProduct(roomId), HotelErrors.PLAN_NO_SUCH_ROOM);
        plan.stopSale(roomId);
        planRepository.updateAndFlush(plan);
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

    public void setPrice(Long hotelId, Long planId, Long roomId, PlanSetPriceCommand command) {
        var plan = planRepository.findByHotelIdAndPlanId(hotelId, planId).orElseThrow(HotelContext::noPrincipalException);
        DomainContext.must(plan.hasProduct(roomId), HotelErrors.PLAN_NO_SUCH_ROOM);
        DomainContext.must(plan.hasStock(roomId), HotelErrors.PLAN_PRODUCT_NO_STOCK);

        var product = plan.requireRoom(2L);
        DomainContext.must(product.getChargeType() == command.getChargeType(), HotelErrors.PLAN_PRODUCT_UN_SUPPORT_CHARGE_TYPE);

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
}
