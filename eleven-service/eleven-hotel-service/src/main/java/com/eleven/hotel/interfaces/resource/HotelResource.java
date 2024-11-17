package com.eleven.hotel.interfaces.resource;

import com.eleven.core.application.query.PageResult;
import com.eleven.core.interfaces.web.Rests;
import com.eleven.core.interfaces.web.annonation.AsRestApi;
import com.eleven.hotel.api.interfaces.request.HotelCreateRequest;
import com.eleven.hotel.api.interfaces.request.HotelQueryRequest;
import com.eleven.hotel.api.interfaces.request.HotelUpdateRequest;
import com.eleven.hotel.api.interfaces.dto.HotelDto;
import com.eleven.hotel.application.command.HotelQuery;
import com.eleven.hotel.application.service.HotelService;
import com.eleven.hotel.application.support.HotelContext;
import com.eleven.hotel.interfaces.convert.HotelConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Slf4j
@AsRestApi
@RequiredArgsConstructor
@Tag(name = "hotel")
@RequestMapping("/hotels")
public class HotelResource {

    private final HotelService hotelService;
    private final HotelConvertor hotelConvertor;

    @Operation(summary = "query hotel")
    @GetMapping
    public PageResult<HotelDto> queryHotel(@ParameterObject @Validated HotelQueryRequest request) {
        var command = HotelQuery.builder()
            .hotelName(request.getHotelName())
            .build();
        return hotelService.query(command).map(hotelConvertor::toDto);
    }


    @Operation(summary = "read hotel")
    @GetMapping("/{hotelId:[0-9]+}")
    public Optional<HotelDto> readHotel(@PathVariable("hotelId") Long hotelId) {
        return hotelService.read(hotelId).map(hotelConvertor::toDto);
    }


    @Operation(summary = "create hotel")
    @PostMapping
    public HotelDto createHotel(@Validated @RequestBody HotelCreateRequest request) {
        var command = hotelConvertor.toCommand(request);
        var hotel = hotelService.create(command);
        return hotelConvertor.toDto(hotel);
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
            .orElseThrow(Rests::throw404);
        hotelService.close(hotelId);
    }
}
