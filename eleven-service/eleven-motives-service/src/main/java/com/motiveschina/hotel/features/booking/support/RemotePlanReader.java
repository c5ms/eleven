package com.motiveschina.hotel.features.booking.support;

import java.util.Optional;
import com.motiveschina.hotel.features.booking.Plan;
import com.motiveschina.hotel.features.booking.PlanReader;
import com.motiveschina.hotel.features.hotel.HotelClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
