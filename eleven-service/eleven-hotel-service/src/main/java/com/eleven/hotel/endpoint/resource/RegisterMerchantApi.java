package com.eleven.hotel.endpoint.resource;

import com.eleven.core.web.annonation.AsMerchantApi;
import com.eleven.hotel.api.endpoint.core.HotelEndpoints;
import com.eleven.hotel.api.endpoint.request.HotelRegisterRequest;
import com.eleven.hotel.api.endpoint.model.RegisterDto;
import com.eleven.hotel.application.command.HotelRegisterCommand;
import com.eleven.hotel.application.service.HotelCommandService;
import com.eleven.hotel.domain.model.admin.Admin;
import com.eleven.hotel.endpoint.convert.HotelConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@AsMerchantApi
@RequiredArgsConstructor
@Tag(name = HotelEndpoints.Tags.REGISTER)
@RequestMapping(HotelEndpoints.Paths.REGISTER)
public class RegisterMerchantApi {

    private final HotelConvertor hotelConvertor;
    private final HotelCommandService hotelCommandService;

    @Operation(summary = "register hotel")
    @PostMapping
    public RegisterDto register(@RequestBody @Validated HotelRegisterRequest request) {
        var command = HotelRegisterCommand.builder()
                .adminContact(new Admin.Contact(request.getAdminName(), request.getAdminEmail(), request.getAdminTel()))
                .hotelName(request.getHotelName())
                .hotelAddress(request.getHotelAddress())
                .build();
        var register = hotelCommandService.register(command);
        return hotelConvertor.toDto(register);
    }

}
