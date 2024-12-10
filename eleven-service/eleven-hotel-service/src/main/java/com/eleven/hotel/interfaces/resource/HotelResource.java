package com.eleven.hotel.interfaces.resource;

import com.eleven.core.interfaces.model.PageRequest;
import com.eleven.core.interfaces.model.PageResponse;
import com.eleven.core.interfaces.web.Rests;
import com.eleven.core.interfaces.web.annonation.AsRestApi;
import com.eleven.hotel.api.interfaces.model.hotel.HotelCreateRequest;
import com.eleven.hotel.api.interfaces.model.hotel.HotelDto;
import com.eleven.hotel.api.interfaces.model.hotel.HotelQueryRequest;
import com.eleven.hotel.api.interfaces.model.hotel.HotelUpdateRequest;
import com.eleven.hotel.application.query.HotelQuery;
import com.eleven.hotel.application.query.filter.HotelFilter;
import com.eleven.hotel.application.service.HotelService;
import com.eleven.hotel.application.support.HotelContext;
import com.eleven.hotel.interfaces.converter.HotelConvertor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Slf4j
@AsRestApi
@RequiredArgsConstructor
@Tag(name = "hotel")
@RequestMapping("/hotels")
public class HotelResource {

    private final HotelQuery hotelQuery;
    private final HotelService hotelService;
    private final HotelConvertor hotelConvertor;

    @Operation(summary = "query hotel")
    @GetMapping
    public PageResponse<HotelDto> queryHotel(@ParameterObject @Validated HotelQueryRequest request,
                                             @ParameterObject @Validated PageRequest pageRequest) {
        var filter = HotelFilter.builder()
                .hotelName(request.getHotelName())
                .build();
        var page = pageRequest.toPagerequest();
        var result = hotelQuery.queryPage(filter, page).map(hotelConvertor::toDto);
        return PageResponse.of(result.getContent(), result.getTotalElements());
    }

    @Operation(summary = "read hotel")
    @GetMapping("/{hotelId:[0-9]+}")
    public Optional<HotelDto> readHotel(@PathVariable("hotelId") Long hotelId) {
        return hotelQuery.read(hotelId).map(hotelConvertor::toDto);
    }

    @PreAuthorize("hasRole('HOTEL')")
    @Operation(summary = "create hotel")
    @PostMapping
    public HotelDto createHotel(@Validated @RequestBody HotelCreateRequest request) {
        var command = hotelConvertor.toCommand(request);
        var hotel = hotelService.create(command);
        return hotelConvertor.toDto(hotel);
    }

    @Operation(summary = "update hotel")
    @PostMapping("/{hotelId:[0-9]+}")
    public HotelDto updateHotel(@PathVariable("hotelId") Long hotelId, @RequestBody @Validated HotelUpdateRequest request) {
        var command = hotelConvertor.toCommand(request);
        var hotel = hotelService.update(hotelId, command);
        return hotelConvertor.toDto(hotel);
    }

    @Operation(summary = "open hotel")
    @PostMapping("/{hotelId:[0-9]+}/commands/open")
    public void openHotel(@PathVariable("hotelId") Long hotelId) {
        hotelQuery.read(hotelId)
                .filter(HotelContext::mustWritable)
                .orElseThrow(Rests::throw404);
        hotelService.open(hotelId);
    }

    @Operation(summary = "close hotel")
    @PostMapping("/{hotelId:[0-9]+]}/commands/close")
    public void closeHotel(@PathVariable("hotelId") Long hotelId) {
        hotelQuery.read(hotelId)
                .filter(HotelContext::mustWritable)
                .orElseThrow(Rests::throw404);
        hotelService.close(hotelId);
    }
}
