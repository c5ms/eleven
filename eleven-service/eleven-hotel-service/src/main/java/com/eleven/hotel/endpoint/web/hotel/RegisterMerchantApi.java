package com.eleven.hotel.endpoint.web.hotel;

import com.eleven.hotel.endpoint.configure.AsMerchantApi;
import com.eleven.hotel.api.endpoint.core.HotelEndpoints;
import com.eleven.hotel.api.endpoint.model.RegisterDto;
import com.eleven.hotel.api.endpoint.request.HotelRegisterRequest;
import com.eleven.hotel.application.command.HotelRegisterCommand;
import com.eleven.hotel.application.service.RegisterService;
import com.eleven.hotel.domain.model.hotel.Register;
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
    private final RegisterService registerService;

    @Operation(summary = "register hotel")
    @PostMapping
    public RegisterDto register(@RequestBody @Validated HotelRegisterRequest request) {
        var command = HotelRegisterCommand.builder()
            .admin(new Register.AdminInformation(request.getAdminName(), request.getAdminEmail(), request.getAdminTel()))
            .hotel(new Register.HotelInformation(request.getHotelName(),request.getHotelAddress() ))
            .build();
        var register = registerService.register(command);
        return hotelConvertor.toDto(register);
    }

}
