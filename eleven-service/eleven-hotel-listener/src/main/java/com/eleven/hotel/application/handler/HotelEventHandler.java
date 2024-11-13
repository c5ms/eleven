package com.eleven.hotel.application.handler;

import com.eleven.hotel.api.interfaces.client.HotelClient;
import com.eleven.hotel.application.service.PlanHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelEventHandler {
    private final HotelClient hotelClient;
    private final PlanHandler planHandler;


}
