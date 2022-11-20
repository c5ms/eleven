package com.eleven.hotel.endpoint.resource;

import com.eleven.core.web.annonation.AsMerchantApi;
import com.eleven.hotel.api.application.model.RegisterDto;
import com.eleven.hotel.api.endpoint.core.HotelEndpoints;
import com.eleven.hotel.api.endpoint.request.HotelRegisterRequest;
import com.eleven.hotel.application.command.HotelRegisterCommand;
import com.eleven.hotel.application.convert.HotelConvertor;
import com.eleven.hotel.application.service.RegisterService;
import com.eleven.hotel.domain.values.Contact;
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

    private final RegisterService registerService;
    private final HotelConvertor hotelConvertor;

    @Operation(summary = "register hotel")
    @PostMapping
    public RegisterDto register(@RequestBody @Validated HotelRegisterRequest request) {
        var command = HotelRegisterCommand.builder()
            .adminName(request.getAdminName())
            .adminContact(Contact.of(request.getAdminEmail(), request.getAdminTel()))
            .hotelName(request.getHotelName())
            .hotelAddress(request.getHotelAddress())
            .build();
        var register = registerService.register(command);
        return hotelConvertor.entities.toDto(register);
    }

}
