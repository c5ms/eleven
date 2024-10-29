package com.eleven.demo.endpoint.rest;

import com.eleven.core.application.command.CommandDispatcher;
import com.eleven.core.application.command.CommandHandleException;
import com.eleven.core.auth.SecurityContext;
import com.eleven.core.auth.Subject;
import com.eleven.core.web.annonation.AsMerchantApi;
import com.eleven.hotel.api.endpoint.model.HotelDto;
import com.eleven.hotel.api.endpoint.internal.HotelClient;
import com.eleven.upms.api.endpoint.UpmsClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/demo")
@AsMerchantApi
public class DemoApi {
    private final UpmsClient upmsClient;
    private final HotelClient hotelClient;

    //    @RolesAllowed("user")
    @GetMapping("/01")
    public Subject _01() {
        return SecurityContext.getCurrentSubject();
    }

    //    @RolesAllowed("user")
    @GetMapping("/02")
    public Optional<HotelDto> _02() {
        return hotelClient.readHotel("038120241003230823000003");
    }


    private final CommandDispatcher commandDispatcher;

    @GetMapping("/03")
    public void _03() throws CommandHandleException {
        ;
    }

}
