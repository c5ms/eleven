package com.eleven.booking.application.support;

import com.eleven.booking.domain.model.booking.Hotel;
import com.eleven.booking.domain.model.booking.HotelReader;
import com.eleven.booking.domain.model.booking.Plan;
import com.eleven.booking.domain.model.booking.PlanReader;
import com.eleven.hotel.api.endpoint.internal.HotelClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RemotePlanReader implements PlanReader {

    private final HotelClient hotelClient;

    @Override
    public Optional<Plan> readPlan(Long hotelId, Long planId) {
          return hotelClient.readPlan(hotelId,planId)
                  .map(RemotePlan::new)
                  ;
    }
}
