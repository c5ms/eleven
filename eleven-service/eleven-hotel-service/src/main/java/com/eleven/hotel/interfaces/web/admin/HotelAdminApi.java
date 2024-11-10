package com.eleven.hotel.interfaces.web.admin;

import com.eleven.core.application.query.PageResult;
import com.eleven.hotel.api.interfaces.model.HotelDto;
import com.eleven.hotel.api.interfaces.request.HotelCreateRequest;
import com.eleven.hotel.api.interfaces.request.HotelQueryRequest;
import com.eleven.hotel.application.command.HotelCreateCommand;
import com.eleven.hotel.application.command.HotelQuery;
import com.eleven.hotel.application.service.HotelService;
import com.eleven.hotel.domain.model.hotel.HotelBasic;
import com.eleven.hotel.domain.model.hotel.HotelPosition;
import com.eleven.hotel.interfaces.convert.HotelConvertor;
import com.eleven.hotel.interfaces.support.AsAdminApi;
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
@Tag(name = "hotel")
@RequestMapping("/hotels")
public class HotelAdminApi {

    private final HotelService hotelService;
    private final HotelConvertor hotelConvertor;

    @Operation(summary = "read hotel")
    @GetMapping("/{hotelId:[0-9]+}")
    public Optional<HotelDto> readHotel(@PathVariable("hotelId") Long hotelId) {
        return hotelService.read(hotelId).map(hotelConvertor::toDto);
    }

    @Operation(summary = "create hotel")
    @PostMapping
    public HotelDto createHotel(@Validated @RequestBody HotelCreateRequest request) {
        var command = HotelCreateCommand.builder()
            .basic(new HotelBasic(
                request.getName(),
                request.getDescription(),
                request.getHeadPicUrl(),
                request.getTotalRooms(),
                request.getCheckIn(),
                request.getCheckOut(),
                request.getEmail(),
                request.getTel())
            )
            .position(new HotelPosition(
                request.getProvince(),
                request.getCity(),
                request.getDistrict(),
                request.getStreet(),
                request.getAddress(),
                request.getLat(),
                request.getLng()
            ))
            .build();
        var hotel = hotelService.create(command);
        return hotelConvertor.toDto(hotel);
    }

    @Operation(summary = "query hotel")
    @GetMapping
    public PageResult<HotelDto> queryHotel(@ParameterObject @Validated HotelQueryRequest request) {
        var command = HotelQuery.builder()
            .hotelName(request.getHotelName())
            .build();
        return hotelService.query(command).map(hotelConvertor::toDto);
    }

}
