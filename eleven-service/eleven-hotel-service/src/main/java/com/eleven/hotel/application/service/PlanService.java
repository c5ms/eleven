package com.eleven.hotel.application.service;

import com.eleven.core.application.query.PageResult;
import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.application.command.PlanAddRoomCommand;
import com.eleven.hotel.application.command.PlanCreateCommand;
import com.eleven.hotel.application.query.PlanQuery;
import com.eleven.hotel.application.support.HotelContext;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.domain.model.hotel.RoomRepository;
import com.eleven.hotel.domain.model.plan.Plan;
import com.eleven.hotel.domain.model.plan.PlanBasic;
import com.eleven.hotel.domain.model.plan.PlanCreator;
import com.eleven.hotel.domain.model.plan.PlanRepository;
import com.github.wenhao.jpa.Specifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
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

    public Optional<Plan> readPlan(Integer hotelId, Integer planId) {
        return planRepository.findByHotelIdAndPlanId(hotelId, planId)
            .filter(HotelContext::mustReadable);
    }

    @Transactional(readOnly = true)
    public PageResult<Plan> queryPage(Integer hotelId, PlanQuery query) {
        hotelRepository.findById(hotelId).orElseThrow(HotelContext::noPrincipalException);

        Specification<Plan> specification = Specifications.<Plan>and()
            .like(StringUtils.isNotBlank(query.getPlanName()), Plan.Fields.basic + "." + PlanBasic.Fields.name, "%" + query.getPlanName() + "%")
            .build();

        var result = planRepository.findAll(specification, PageRequest.of(query.getPage(), query.getSize() - 1));
        return PageResult.of(result.getContent(), result.getTotalElements());
    }

    @Transactional(rollbackFor = Exception.class)
    public Plan createPlan(Integer hotelId, PlanCreateCommand command) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(HotelContext::noPrincipalException);
        var plan = PlanCreator.normal()
            .hotelId(hotel.getHotelId())
            .salePeriod(command.getSellPeriod())
            .preSellPeriod(command.getPreSellPeriod())
            .stayPeriod(command.getStayPeriod())
            .basic(command.getBasic())
            .stock(command.getStock())
            .create();

        plan.openChannel(SaleChannel.DP,SaleChannel.DH);

        if (CollectionUtils.isNotEmpty(command.getRooms())) {
            var rooms = roomRepository.findAllById(command.getRooms());
            for (Room room : rooms) {
                var planRoom = plan.addRoom(room);

                planRoom.setPrice(
                    SaleChannel.DH,
                    BigDecimal.valueOf(RandomUtils.nextLong(0, 2000)),
                    BigDecimal.valueOf(RandomUtils.nextLong(0, 2000)),
                    BigDecimal.valueOf(RandomUtils.nextLong(0, 2000)),
                    BigDecimal.valueOf(RandomUtils.nextLong(0, 2000)),
                    BigDecimal.valueOf(RandomUtils.nextLong(0, 2000))
                );
                planRoom.setPrice(SaleChannel.DP, BigDecimal.valueOf(RandomUtils.nextLong(0, 2000)));
            }
        }


        planRepository.persist(plan);
        return plan;
    }

    @Transactional(rollbackFor = Exception.class)
    public void addRoom(Integer hotelId, Integer planId, PlanAddRoomCommand command) {
        var plan = planRepository.findByHotelIdAndPlanId(hotelId, planId).orElseThrow(HotelContext::noPrincipalException);
        var room = roomRepository.findByHotelIdAndRoomId(hotelId, command.getRoomId()).orElseThrow(HotelContext::noEntityException);
        var stock = command.getStock();
        plan.addRoom(room, stock);
        planRepository.persist(plan);
    }


}
