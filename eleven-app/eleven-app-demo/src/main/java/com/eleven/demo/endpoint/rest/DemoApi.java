package com.eleven.demo.endpoint.rest;

import com.eleven.hotel.api.endpoint.internal.HotelClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/demo")
public class DemoApi {
    private final HotelClient hotelClient;


}
