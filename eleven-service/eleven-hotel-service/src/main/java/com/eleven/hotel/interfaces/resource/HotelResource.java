package com.eleven.hotel.interfaces.resource;

import com.eleven.core.interfaces.model.PageRequest;
import com.eleven.core.interfaces.model.PageResponse;
import com.eleven.core.interfaces.web.Rests;
import com.eleven.core.interfaces.web.annonation.AsRestApi;
import com.eleven.hotel.api.interfaces.request.HotelCreateRequest;
import com.eleven.hotel.api.interfaces.request.HotelQueryRequest;
import com.eleven.hotel.api.interfaces.request.HotelUpdateRequest;
import com.eleven.hotel.api.interfaces.dto.HotelDto;
import com.eleven.hotel.application.command.HotelQuery;
import com.eleven.hotel.application.service.HotelService;
import com.eleven.hotel.application.query.HotelQuery;
import com.eleven.hotel.application.query.filter.HotelFilter;
import com.eleven.hotel.application.support.HotelContext;
import com.eleven.hotel.domain.model.hotel.HotelBasic;
import com.eleven.hotel.domain.model.hotel.values.Address;
import com.eleven.hotel.domain.model.hotel.values.CheckPolicy;
import com.eleven.hotel.domain.model.hotel.values.Position;
import com.eleven.hotel.interfaces.assembler.HotelAssembler;
import com.eleven.hotel.interfaces.converter.HotelConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Optional;


@Slf4j
@AsRestApi
@RequiredArgsConstructor
@Tag(name = "hotel")
@RequestMapping("/hotels")
public class HotelResource {

    private final HotelQuery hotelQuery;
    private final HotelService hotelService;
    private final HotelAssembler hotelAssembler;

    @Operation(summary = "query hotel")
    @GetMapping
    public PageResponse<HotelDto> queryHotel(@ParameterObject @Validated HotelQueryRequest request,
                                             @ParameterObject @Validated PageRequest pageRequest) {
        var filter = HotelFilter.builder()
                .hotelName(request.getHotelName())
                .build();
        var page = hotelQuery.queryPage(filter, pageRequest.toPagerequest()).map(hotelAssembler::assembleDto);
        return PageResponse.of(page.getContent(), page.getTotalElements());
    }


    @Operation(summary = "read hotel")
    @GetMapping("/{hotelId:[0-9]+}")
    public Optional<HotelDto> readHotel(@PathVariable("hotelId") Long hotelId) {
        return hotelQuery.read(hotelId).map(hotelAssembler::assembleDto);
    }


    @Operation(summary = "create hotel")
    @PostMapping
    public HotelDto createHotel(@Validated @RequestBody HotelCreateRequest request) {
        var command = HotelCreateCommand.builder()
            .basic(new HotelBasic(
                request.getName(),
                request.getDescription(),
                request.getEmail(),
                request.getPhone(),
                request.getTotalRoomQuantity(),
                request.getWhenBuilt(),
                request.getLastRenovation(),
                request.getStarRating(),
                request.getBuildingArea()
            ))
            .position(new Position(
                request.getLatitude(),
                request.getLongitude()
            ))
            .address(new Address(
                request.getCountry(),
                request.getProvince(),
                request.getCity(),
                request.getLocation(),
                request.getAddress()
            ))
            .checkPolicy(new CheckPolicy(
                LocalTime.parse(request.getCheckIn(), HotelConstants.FORMATTER_HOUR_MINUTES),
                LocalTime.parse(request.getCheckOut(), HotelConstants.FORMATTER_HOUR_MINUTES)
            ))
            .build();
        var hotel = hotelService.create(command);
        return hotelAssembler.assembleDto(hotel);
    }


    @Operation(summary = "update hotel")
    @PostMapping("/{hotelId:[0-9]+}")
    public void update(@PathVariable("hotelId") Long hotelId, @RequestBody @Validated HotelUpdateRequest request) {
        var command = HotelUpdateCommand.builder()
            .basic(new HotelBasic(
                request.getName(),
                request.getDescription(),
                request.getEmail(),
                request.getPhone(),
                request.getTotalRoomQuantity(),
                request.getWhenBuilt(),
                request.getLastRenovation(),
                request.getStarRating(),
                request.getBuildingArea()
            ))
            .position(new Position(
                request.getLatitude(),
                request.getLongitude()
            ))
            .address(new Address(
                request.getCountry(),
                request.getProvince(),
                request.getCity(),
                request.getLocation(),
                request.getAddress()
            ))
            .checkPolicy(new CheckPolicy(
                request.getCheckIn(),
                request.getCheckOut()
            ))
            .build();
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
