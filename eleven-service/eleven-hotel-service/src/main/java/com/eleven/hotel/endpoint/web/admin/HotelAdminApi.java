package com.eleven.hotel.endpoint.web.admin;

import com.eleven.core.application.query.PageResult;
import com.eleven.hotel.endpoint.support.AsAdminApi;
import com.eleven.hotel.api.endpoint.core.HotelEndpoints;
import com.eleven.hotel.api.endpoint.model.HotelDto;
import com.eleven.hotel.api.endpoint.request.HotelCreateRequest;
import com.eleven.hotel.api.endpoint.request.HotelQueryRequest;
import com.eleven.hotel.application.service.HotelService;
import com.eleven.hotel.endpoint.convert.HotelConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@AsAdminApi
@RequiredArgsConstructor
@Tag(name = HotelEndpoints.Tags.HOTEL)
@RequestMapping(HotelEndpoints.Paths.HOTEL)
public class HotelAdminApi {

    private final HotelService hotelService;
    private final HotelConvertor hotelConvertor;

    @Operation(summary = "read hotel")
    @GetMapping("/{hotelId:[0-9]+}")
    public Optional<HotelDto> readHotel(@PathVariable("hotelId") Integer hotelId) {
        return hotelService.read(hotelId).map(hotelConvertor::toDto);
    }

    @Operation(summary = "create hotel")
    @PostMapping
    public HotelDto createHotel(@Validated @RequestBody HotelCreateRequest request) {
        var command = hotelConvertor.toCommand(request);
        var hotel=hotelService.create(command);
        return hotelConvertor.toDto(hotel);
    }

    @Operation(summary = "query hotel")
    @GetMapping
    public PageResult<HotelDto> queryHotel(@ParameterObject @Validated HotelQueryRequest request) {
        var command = hotelConvertor.toCommand(request);
        return hotelService.query(command).map(hotelConvertor::toDto);
    }

}
