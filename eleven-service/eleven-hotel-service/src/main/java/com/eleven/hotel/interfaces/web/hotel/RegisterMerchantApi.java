package com.eleven.hotel.interfaces.web.hotel;

import com.eleven.hotel.api.interfaces.model.RegisterDto;
import com.eleven.hotel.api.interfaces.request.HotelRegisterRequest;
import com.eleven.hotel.application.service.RegisterService;
import com.eleven.hotel.interfaces.convert.HotelConvertor;
import com.eleven.hotel.interfaces.support.AsMerchantApi;
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
@Tag(name = "register")
@RequestMapping("/registers")
public class RegisterMerchantApi {

    private final HotelConvertor hotelConvertor;
    private final RegisterService registerService;

    @Operation(summary = "register hotel")
    @PostMapping
    public RegisterDto register(@RequestBody @Validated HotelRegisterRequest request) {
        var command = hotelConvertor.toCommand(request);
        var register = registerService.register(command);
        return hotelConvertor.toDto(register);
    }

}
