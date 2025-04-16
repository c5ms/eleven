package com.eleven.demo.demo;

import com.eleven.demo.hotel.HotelClient;
import com.eleven.framework.web.annonation.AsRestApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@AsRestApi
@RequestMapping
@RequiredArgsConstructor
public class DemoResource {

    private final HotelClient hotelClient;

    @GetMapping("/rooms")
    public Optional<String> rooms(String roomId) {
        return hotelClient.readPlan(1L, 1L);
    }

}
