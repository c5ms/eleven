package com.eleven.hotel.interfaces.resource;

import com.eleven.core.interfaces.model.PageRequest;
import com.eleven.core.interfaces.model.PageResponse;
import com.eleven.core.interfaces.web.Rests;
import com.eleven.core.interfaces.web.annonation.AsRestApi;
import com.eleven.hotel.api.interfaces.dto.HotelDto;
import com.eleven.hotel.api.interfaces.request.HotelCreateRequest;
import com.eleven.hotel.api.interfaces.request.HotelQueryRequest;
import com.eleven.hotel.api.interfaces.request.HotelUpdateRequest;
import com.eleven.hotel.application.command.HotelCreateCommand;
import com.eleven.hotel.application.command.HotelUpdateCommand;
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
        var page = hotelQuery.queryPage(filter, pageRequest.toPagerequest()).map(hotelConvertor::assembleDto);
        return PageResponse.of(page.getContent(), page.getTotalElements());
    }


    @Operation(summary = "read hotel")
    @GetMapping("/{hotelId:[0-9]+}")
    public Optional<HotelDto> readHotel(@PathVariable("hotelId") Long hotelId) {
        return hotelQuery.read(hotelId).map(hotelConvertor::assembleDto);
    }


    @Operation(summary = "create hotel")
    @PostMapping
    public HotelDto createHotel(@Validated @RequestBody HotelCreateRequest request) {
        var command = HotelCreateCommand.builder()
            .basic(hotelConvertor.toHotelBasic(request.getHotelBasic()))
            .address(hotelConvertor.toAddress(request.getAddress()))
            .position(hotelConvertor.toPosition(request.getPosition()))
            .checkPolicy(hotelConvertor.toCheckPolicy(request.getCheckPolicy()) )
            .build();
        var hotel = hotelService.create(command);
        return hotelConvertor.assembleDto(hotel);
    }


    @Operation(summary = "update hotel")
    @PostMapping("/{hotelId:[0-9]+}")
    public void update(@PathVariable("hotelId") Long hotelId, @RequestBody @Validated HotelUpdateRequest request) {
        var command = hotelConvertor.toHotelUpdateCommand(request);
        hotelService.update(hotelId, command);
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
