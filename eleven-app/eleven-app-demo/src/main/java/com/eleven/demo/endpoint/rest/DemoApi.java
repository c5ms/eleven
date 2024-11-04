package com.eleven.demo.endpoint.rest;

import com.eleven.hotel.api.endpoint.internal.HotelClient;
import com.eleven.hotel.api.application.model.HotelDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/demo")
public class DemoApi {
    private final HotelClient hotelClient;

    @RequestMapping("/hello")
    public Optional<HotelDto> hello(){
        return hotelClient.readHotel(2L);
    }

}
