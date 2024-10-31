package com.eleven.hotel.endpoint.web.hotel;

import com.eleven.core.application.ApplicationHelper;
import com.eleven.core.web.WebHelper;
import com.eleven.hotel.endpoint.configure.AsMerchantApi;
import com.eleven.hotel.api.endpoint.core.HotelEndpoints;
import com.eleven.hotel.api.endpoint.model.HotelDto;
import com.eleven.hotel.api.endpoint.request.HotelUpdateRequest;
import com.eleven.hotel.application.command.HotelUpdateCommand;
import com.eleven.hotel.application.service.HotelService;
import com.eleven.hotel.domain.model.hotel.Hotel;
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
    @GetMapping("/{hotelId}")
    public Optional<HotelDto> readHotel(@PathVariable("hotelId") Integer hotelId) {
        return hotelService.read(hotelId)
                .filter(ApplicationHelper::mustReadable)
                .map(hotelConvertor::toDto);
    }

    @Operation(summary = "update hotel")
    @PostMapping("/{hotelId}")
    public void update(@PathVariable("hotelId") Integer hotelId, @RequestBody @Validated HotelUpdateRequest request) {
        hotelService.read(hotelId)
                .filter(ApplicationHelper::mustReadable)
                .orElseThrow(WebHelper::notFoundException);

        var command = HotelUpdateCommand.builder()
            .description(new Hotel.Description(
                request.getName(),
                request.getDescription(),
                request.getHeadPicUrl(),
                request.getRoomNumber(),
                request.getCheckIn(),
                request.getCheckOut(),
                request.getEmail(),
                request.getTel())
            )
            .position(new Hotel.Position(
                request.getProvince(),
                request.getCity(),
                request.getDistrict(),
                request.getStreet(),
                request.getAddress(),
                request.getLat(),
                request.getLng()
            ))
            .build();
        hotelService.update(hotelId,command);
    }

    @Operation(summary = "open hotel")
    @PostMapping("/{hotelId}/operations/open")
    public void openHotel(@PathVariable("hotelId") Integer hotelId) {
        hotelService.read(hotelId)
                .filter(ApplicationHelper::mustWritable)
                .orElseThrow(WebHelper::notFoundException);
        hotelService.open(hotelId);
    }

    @Operation(summary = "close hotel")
    @PostMapping("/{hotelId}/operations/close")
    public void closeHotel(@PathVariable("hotelId") Integer hotelId) {
        hotelService.read(hotelId)
                .filter(ApplicationHelper::mustWritable)
                .orElseThrow(WebHelper::notFoundException);
        hotelService.close(hotelId);
    }

}
