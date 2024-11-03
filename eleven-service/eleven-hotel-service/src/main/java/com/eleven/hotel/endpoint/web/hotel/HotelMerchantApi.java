package com.eleven.hotel.endpoint.web.hotel;

import cn.hutool.extra.spring.SpringUtil;
import com.eleven.hotel.api.domain.model.SaleChannel;
import com.eleven.hotel.application.command.BookingCommand;
import com.eleven.hotel.application.service.BookingService;
import com.eleven.hotel.application.support.HotelContext;
import com.eleven.core.web.WebContext;
import com.eleven.hotel.api.endpoint.core.HotelEndpoints;
import com.eleven.hotel.api.endpoint.model.HotelDto;
import com.eleven.hotel.api.endpoint.request.HotelUpdateRequest;
import com.eleven.hotel.application.service.HotelService;
import com.eleven.hotel.endpoint.support.AsMerchantApi;
import com.eleven.hotel.endpoint.convert.HotelConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Slf4j
@AsMerchantApi
@RequiredArgsConstructor
@Tag(name = HotelEndpoints.Tags.HOTEL)
@RequestMapping(HotelEndpoints.Paths.HOTEL)
public class HotelMerchantApi {

    private final HotelService hotelService;
    private final HotelConvertor hotelConvertor;

    @Operation(summary = "read hotel")
    @GetMapping("/{hotelId:[0-9]+}")
    public Optional<HotelDto> readHotel(@PathVariable("hotelId") Long hotelId) {
        return hotelService.read(hotelId).map(hotelConvertor::toDto);
    }

    @Operation(summary = "update hotel")
    @PostMapping("/{hotelId:[0-9]+}")
    public void update(@PathVariable("hotelId") Long hotelId, @RequestBody @Validated HotelUpdateRequest request) {
        var command = hotelConvertor.toCommand(request);
        hotelService.update(hotelId, command);
    }

    @Operation(summary = "open hotel")
    @PostMapping("/{hotelId:[0-9]+}/commands/open")
    public void openHotel(@PathVariable("hotelId") Long hotelId) {
        hotelService.open(hotelId);
    }

    @Operation(summary = "close hotel")
    @PostMapping("/{hotelId:[0-9]+]}/commands/close")
    public void closeHotel(@PathVariable("hotelId") Long hotelId) {
        hotelService.read(hotelId)
            .filter(HotelContext::mustWritable)
            .orElseThrow(WebContext::notFoundException);
        hotelService.close(hotelId);
    }

    @Operation(summary = "test")
    @GetMapping("/test")
    public void test() {
        SpringUtil.getBean(BookingService.class).book(BookingCommand.builder()
                .hotelId(2L)
                .planId(3410L)
                .personCount(4)
                .saleChannel(SaleChannel.DH)
            .build());
    }

}
