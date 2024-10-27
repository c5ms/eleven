package com.eleven.hotel.endpoint.resource;

import com.eleven.core.web.annonation.AsMerchantApi;
import com.eleven.hotel.api.endpoint.core.HotelEndpoints;
import com.eleven.hotel.api.endpoint.model.HotelDto;
import com.eleven.hotel.api.endpoint.request.HotelUpdateRequest;
import com.eleven.hotel.application.command.HotelCloseCommand;
import com.eleven.hotel.application.command.HotelOpenCommand;
import com.eleven.hotel.application.command.HotelUpdateCommand;
import com.eleven.hotel.application.service.HotelCommandService;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
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

    private final HotelConvertor hotelConvertor;
    private final HotelRepository hotelRepository;

    private final HotelCommandService hotelCommandService;

    @Operation(summary = "read hotel")
    @GetMapping("/{hotelId}")
    public Optional<HotelDto> readHotel(@PathVariable("hotelId") String hotelId) {
        return hotelRepository.findById(hotelId).map(hotelConvertor::toDto);
    }

    @Operation(summary = "update hotel")
    @PostMapping("/{hotelId}")
    public void update(@PathVariable("hotelId") String hotelId, @RequestBody @Validated HotelUpdateRequest request) {
        var command = HotelUpdateCommand.builder()
            .hotelId(hotelId)
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
        hotelCommandService.update(command);
    }


    @Operation(summary = "open hotel")
    @PostMapping("/{hotelId}/operations/open")
    public void openHotel(@PathVariable("hotelId") String hotelId) {
        var command = HotelOpenCommand.builder()
            .hotelId(hotelId)
            .build();
        hotelCommandService.open(command);
    }

    @Operation(summary = "close hotel")
    @PostMapping("/{hotelId}/operations/close")
    public void closeHotel(@PathVariable("hotelId") String hotelId) {
        var command = HotelCloseCommand.builder()
            .hotelId(hotelId)
            .build();
        hotelCommandService.close(command);
    }

}
