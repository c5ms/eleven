package com.eleven.hotel.application.service;

import cn.hutool.json.JSONUtil;
import com.eleven.core.event.ApplicationEvent;
import com.eleven.hotel.api.endpoint.internal.HotelClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelClient hotelClient;

    @EventListener
    public void on(ApplicationEvent event) {
    }

}
