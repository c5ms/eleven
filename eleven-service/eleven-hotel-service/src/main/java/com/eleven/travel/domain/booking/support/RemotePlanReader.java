package com.eleven.travel.domain.booking.support;

import com.eleven.travel.domain.booking.Plan;
import com.eleven.travel.domain.booking.PlanReader;
import com.eleven.travel.domain.hotel.HotelClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * currently use a simple remote plan, the plan class uses hotelClient inside to handle the calculate logic.
 * Tt will be replaced with a local implementation in the further.
 * TODO replace remote plan with local implementation based on nosql(but not in-memory) like mongodb etc.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RemotePlanReader implements PlanReader {

    private final HotelClient hotelClient;

    @Override
    public Optional<Plan> readPlan(Long hotelId, Long planId, Long roomId) {
        return hotelClient.readPlan(hotelId, planId).map(dto -> new RemotePlan(dto, roomId, hotelClient));
    }

}
