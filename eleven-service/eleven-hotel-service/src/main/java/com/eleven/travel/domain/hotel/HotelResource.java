package com.eleven.travel.domain.hotel;

import com.eleven.framework.web.model.PageRequest;
import com.eleven.framework.web.model.PageResponse;
import com.eleven.framework.web.Rests;
import com.eleven.framework.web.annonation.AsRestApi;
import com.eleven.travel.core.support.ContextSupport;
import com.eleven.travel.domain.hotel.request.HotelCreateRequest;
import com.eleven.travel.domain.hotel.request.HotelQueryRequest;
import com.eleven.travel.domain.hotel.request.HotelUpdateRequest;
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

    private final HotelFinder hotelFinder;
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
        var result = hotelFinder.queryPage(filter, page).map(hotelConvertor::toDto);
        return PageResponse.of(result.getContent(), result.getTotalElements());
    }

    @Operation(summary = "read hotel")
    @GetMapping("/{hotelId:[0-9]+}")
    public Optional<HotelDto> readHotel(@PathVariable("hotelId") Long hotelId) {
        return hotelFinder.read(hotelId).map(hotelConvertor::toDto);
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
    public HotelDto updateHotel(@PathVariable("hotelId") Long hotelId, @RequestBody @Validated HotelUpdateRequest request) {
        var command = hotelConvertor.toCommand(request);
        var hotel = hotelService.update(hotelId, command);
        return hotelConvertor.toDto(hotel);
    }

    @Operation(summary = "open hotel")
    @PostMapping("/{hotelId:[0-9]+}/commands/open")
    public void openHotel(@PathVariable("hotelId") Long hotelId) {
        hotelFinder.read(hotelId)
                .filter(ContextSupport::mustWritable)
                .orElseThrow(Rests::throw404);
        hotelService.open(hotelId);
    }

    @Operation(summary = "close hotel")
    @PostMapping("/{hotelId:[0-9]+]}/commands/close")
    public void closeHotel(@PathVariable("hotelId") Long hotelId) {
        hotelFinder.read(hotelId)
                .filter(ContextSupport::mustWritable)
                .orElseThrow(Rests::throw404);
        hotelService.close(hotelId);
    }
}
