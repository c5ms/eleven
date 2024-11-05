package com.eleven.hotel.application.handler;

import com.eleven.hotel.api.application.event.HotelCreatedEvent;
import com.eleven.hotel.api.application.event.PlanCreatedEvent;
import com.eleven.hotel.api.endpoint.internal.HotelClient;
import com.eleven.hotel.application.service.PlanHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelEventHandler {
    private final HotelClient hotelClient;
    private final PlanHandler planHandler;


    @EventListener
    public void on(PlanCreatedEvent event) {
        hotelClient.readPlan(event.getHotelId(), event.getPlanId()).ifPresent(planHandler::sync);
    }

    @EventListener
    public void on(HotelCreatedEvent event) {

    }

}
