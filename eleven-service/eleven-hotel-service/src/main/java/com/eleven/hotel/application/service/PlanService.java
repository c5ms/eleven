package com.eleven.hotel.application.service;

import com.eleven.core.application.query.PageResult;
import com.eleven.core.domain.DomainContext;
import com.eleven.hotel.api.application.error.HotelErrors;
import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.application.command.PlanAddRoomCommand;
import com.eleven.hotel.application.command.PlanCreateCommand;
import com.eleven.hotel.application.query.PlanQuery;
import com.eleven.hotel.application.support.HotelContext;
import com.eleven.hotel.domain.manager.PlanManager;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.domain.model.hotel.RoomRepository;
import com.eleven.hotel.domain.model.plan.InventoryRepository;
import com.eleven.hotel.domain.model.plan.Plan;
import com.eleven.hotel.domain.model.plan.PlanBasic;
import com.eleven.hotel.domain.model.plan.PlanRepository;
import com.eleven.hotel.domain.values.StockAmount;
import com.github.wenhao.jpa.Specifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
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
    private final RoomRepository roomRepository;
    private final PlanManager planManager;
    private final InventoryRepository inventoryRepository;

    public Optional<Plan> readPlan(Integer hotelId, Integer planId) {
        return planRepository.findByHotelIdAndPlanId(hotelId, planId)
            .filter(HotelContext::mustReadable);
    }

    @Transactional(readOnly = true)
    public PageResult<Plan> queryPage(Integer hotelId, PlanQuery query, Pageable pageable) {
        hotelRepository.findById(hotelId).orElseThrow(HotelContext::noPrincipalException);
        Specification<Plan> specification = Specifications.<Plan>and()
            .like(StringUtils.isNotBlank(query.getPlanName()), Plan.Fields.basic + "." + PlanBasic.Fields.name, "%" + query.getPlanName() + "%")
            .build();
        var pagination = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()).withSort(Sort.by(Plan.Fields.planId).descending());
        var result = planRepository.findAll(specification, pagination);
        return PageResult.of(result.getContent(), result.getTotalElements());
    }

    @Transactional(rollbackFor = Exception.class)
    public Plan createPlan(Integer hotelId, PlanCreateCommand command) {
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

            for (Room room : rooms) {
                if (command.getChannels().contains(SaleChannel.DH)) {
                    plan.setPrice(
                        room.getRoomId(),
                        SaleChannel.DH,
                        BigDecimal.valueOf(RandomUtils.nextLong(0, 2000)),
                        BigDecimal.valueOf(RandomUtils.nextLong(0, 2000)),
                        BigDecimal.valueOf(RandomUtils.nextLong(0, 2000)),
                        BigDecimal.valueOf(RandomUtils.nextLong(0, 2000)),
                        BigDecimal.valueOf(RandomUtils.nextLong(0, 2000))
                    );
                }

                if (command.getChannels().contains(SaleChannel.DP)) {
                    plan.setPrice(room.getRoomId(), SaleChannel.DP, BigDecimal.valueOf(RandomUtils.nextLong(0, 2000)));
                }
            }

        }

        // persist the plan
        planRepository.persistAndFlush(plan);

        // initialize the inventory
        var inventories = plan.createInventories();
        inventoryRepository.persistAll(inventories);

        // return back the created plan
        return plan;
    }

    @Transactional(rollbackFor = Exception.class)
    public void addRoom(Integer hotelId, Integer planId, PlanAddRoomCommand command) {
        var plan = planRepository.findByHotelIdAndPlanId(hotelId, planId).orElseThrow(HotelContext::noPrincipalException);
        var room = roomRepository.findByHotelIdAndRoomId(hotelId, command.getRoomId()).orElseThrow(HotelContext::noEntityException);
        var stock = command.getStock();
        plan.addProduct(room.getRoomId(), stock);
        planRepository.persist(plan);
    }

    @Transactional(rollbackFor = Exception.class)
    public void startSale(Integer hotelId, Integer planId, Integer roomId) {
        var plan = planRepository.findByHotelIdAndPlanId(hotelId, planId).orElseThrow(HotelContext::noPrincipalException);
        DomainContext.must(plan.hasProduct(roomId), HotelErrors.PLAN_NO_SUCH_ROOM);
        DomainContext.must(plan.hasStock(roomId), HotelErrors.PLAN_PRODUCT_NO_STOCK);
        plan.startSale(roomId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void stopSale(Integer hotelId, Integer planId, Integer roomId) {
        var plan = planRepository.findByHotelIdAndPlanId(hotelId, planId).orElseThrow(HotelContext::noPrincipalException);
        DomainContext.must(plan.hasProduct(roomId), HotelErrors.PLAN_NO_SUCH_ROOM);
        plan.stopSale(roomId);
    }
}
