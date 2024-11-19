package com.eleven.hotel.application.service;

import com.eleven.hotel.api.domain.enums.SaleChannel;
import com.eleven.hotel.application.command.PlanCreateCommand;
import com.eleven.hotel.application.command.PlanSetPriceCommand;
import com.eleven.hotel.application.command.PlanUpdateCommand;
import com.eleven.hotel.application.support.HotelContext;
import com.eleven.hotel.domain.errors.HotelErrors;
import com.eleven.hotel.domain.manager.PlanManager;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.model.hotel.RoomKey;
import com.eleven.hotel.domain.model.hotel.RoomRepository;
import com.eleven.hotel.domain.model.plan.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class PlanService {

    private final PlanRepository planRepository;
    private final HotelRepository hotelRepository;
    private final PlanInventoryRepository planInventoryRepository;

    private final PlanManager planManager;
    private final RoomRepository roomRepository;

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
        planManager.takeStock(plan);
        return plan;
    }

    public Plan updatePlan(PlanKey planKey, PlanUpdateCommand command) {
        var plan = planRepository.findByKey(planKey).orElseThrow(HotelContext::noPrincipalException);
        plan.update(command.getPatch());

        command.getRooms().stream()
                .filter(roomId -> plan.findRoom(roomId).isEmpty())
                .map(roomId -> RoomKey.of(planKey.getHotelId(), roomId))
                .map(roomKey -> roomRepository.findByRoomKey(roomKey).orElseThrow(HotelErrors.ROOM_NOT_FOUND::toException))
                .forEach(plan::addRoom);
        plan.removeRoom(product -> !command.getRooms().contains(product.getKey().getRoomId()));

        planManager.validate(plan);
        planRepository.saveAndFlush(plan);
        planManager.takeStock(plan);
        return plan;
    }

    public void deletePlan(PlanKey planKey) {
        var plan = planRepository.findByKey(planKey).orElseThrow(HotelContext::noPrincipalException);
        planRepository.delete(plan);
        planInventoryRepository.deleteByPlanKey(plan.toKey());
    }

    public void startSale(PlanKey planKey, Long roomId) {
        var plan = planRepository.findByKey(planKey).orElseThrow(HotelContext::noPrincipalException);
        plan.findRoom(roomId).orElseThrow(HotelContext::noPrincipalException);
        plan.startSale(roomId);
        planRepository.saveAndFlush(plan);
    }

    public void stopSale(PlanKey planKey, Long roomId) {
        var plan = planRepository.findByKey(planKey).orElseThrow(HotelContext::noPrincipalException);
        plan.findRoom(roomId).orElseThrow(HotelContext::noPrincipalException);
        plan.stopSale(roomId);
        planRepository.saveAndFlush(plan);
    }

    public void setPrice(PlanKey planKey, Long roomId, PlanSetPriceCommand command) {
        var plan = planRepository.findByKey(planKey).orElseThrow(HotelContext::noPrincipalException);
        var room = plan.findRoom(roomId).orElseThrow(HotelContext::noPrincipalException);
        switch (command.getChargeType()) {
            case BY_PERSON ->
                    room.setPrice(command.getSaleChannel(), command.getOnePersonPrice(), command.getTwoPersonPrice(),
                            command.getThreePersonPrice(), command.getFourPersonPrice(), command.getFivePersonPrice());
            case BY_ROOM -> room.setPrice(command.getSaleChannel(), command.getWholeRoomPrice());
        }
        planRepository.saveAndFlush(plan);
    }

    public BigDecimal chargeRoom(ProductKey productKey, SaleChannel saleChannel, int persons) {
        var plan = planRepository.findByKey(productKey.toPlanKey()).orElseThrow(HotelContext::noPrincipalException);
        var product = plan.findRoom(productKey.getRoomId()).orElseThrow(HotelContext::noPrincipalException);
        return product.priceOf(saleChannel).map(price -> price.charge(persons)).orElse(BigDecimal.ZERO);
    }

}
