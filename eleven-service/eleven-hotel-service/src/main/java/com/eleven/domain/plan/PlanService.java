package com.eleven.domain.plan;

import com.eleven.common.domain.SaleChannel;
import com.eleven.common.support.ContextSupport;
import com.eleven.domain.hotel.HotelRepository;
import com.eleven.domain.plan.command.PlanCreateCommand;
import com.eleven.domain.plan.command.PlanSetPriceCommand;
import com.eleven.domain.plan.command.PlanUpdateCommand;
import com.eleven.domain.product.ProductKey;
import com.eleven.domain.product.ProductManager;
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

    private final PlanManager planManager;
    private final ProductManager productManager;

    public Plan createPlan(Long hotelId, PlanCreateCommand command) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(ContextSupport::noPrincipalException);
        var plan = Plan.normal()
                .hotelId(hotel.getHotelId())
                .salePeriod(command.getSalePeriod())
                .preSellPeriod(command.getPreSalePeriod())
                .stayPeriod(command.getStayPeriod())
                .basic(command.getBasic())
                .stock(command.getStock())
                .saleChannels(command.getChannels())
                .create();
        planManager.validate(plan);
        planRepository.saveAndFlush(plan);
        productManager.createProducts(plan, command.getRooms());
        return plan;
    }

    public Plan updatePlan(PlanKey planKey, PlanUpdateCommand command) {
        var plan = planRepository.findByKey(planKey).orElseThrow(ContextSupport::noPrincipalException);
        plan.update(command);
        planManager.validate(plan);
        planRepository.saveAndFlush(plan);
        productManager.createProducts(plan, command.getRooms());
        return plan;
    }

    public void deletePlan(PlanKey planKey) {
        var plan = planRepository.findByKey(planKey).orElseThrow(ContextSupport::noPrincipalException);
        planRepository.delete(plan);
    }

    public void startSale(PlanKey planKey, Long roomId) {
        var plan = planRepository.findByKey(planKey).orElseThrow(ContextSupport::noPrincipalException);
//        plan.findRoom(roomId).orElseThrow(HotelContext::noPrincipalException);
//        plan.startSale(roomId);
        planRepository.saveAndFlush(plan);
    }

    public void stopSale(PlanKey planKey, Long roomId) {
        var plan = planRepository.findByKey(planKey).orElseThrow(ContextSupport::noPrincipalException);
//        plan.findRoom(roomId).orElseThrow(HotelContext::noPrincipalException);
//        plan.stopSale(roomId);
        planRepository.saveAndFlush(plan);
    }

    public void setPrice(PlanKey planKey, Long roomId, PlanSetPriceCommand command) {
        var plan = planRepository.findByKey(planKey).orElseThrow(ContextSupport::noPrincipalException);
//        var room = plan.findRoom(roomId).orElseThrow(HotelContext::noPrincipalException);
//        switch (command.getChargeType()) {
//            case BY_PERSON ->
//                    room.setPrice(command.getSaleChannel(), command.getOnePersonPrice(), command.getTwoPersonPrice(),
//                            command.getThreePersonPrice(), command.getFourPersonPrice(), command.getFivePersonPrice());
//            case BY_ROOM -> room.setPrice(command.getSaleChannel(), command.getWholeRoomPrice());
//        }
        planRepository.saveAndFlush(plan);
    }

    public BigDecimal chargeRoom(ProductKey productKey, SaleChannel saleChannel, int persons) {
        var plan = planRepository.findByKey(productKey.toPlanKey()).orElseThrow(ContextSupport::noPrincipalException);
//        var product = plan.findRoom(productKey.getRoomId()).orElseThrow(HotelContext::noPrincipalException);
//        return product.priceOf(saleChannel).map(price -> price.charge(persons)).orElse(BigDecimal.ZERO);
        return BigDecimal.ZERO;
    }

}
