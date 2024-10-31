package com.eleven.demo.endpoint.rest;

import com.eleven.core.application.command.CommandDispatcher;
import com.eleven.core.application.command.CommandHandleException;
import com.eleven.core.authorization.SecurityContext;
import com.eleven.core.authorization.Subject;
import com.eleven.hotel.api.endpoint.internal.HotelClient;
import com.eleven.hotel.api.endpoint.model.HotelDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/demo")
public class DemoApi {
    private final HotelClient hotelClient;


}
