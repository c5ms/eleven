package com.eleven.hotel.endpoint.resource;

import com.eleven.core.web.annonation.AsMerchantApi;
import com.eleven.hotel.api.application.model.HotelDto;
import com.eleven.hotel.api.endpoint.core.HotelEndpoints;
import com.eleven.hotel.api.endpoint.request.HotelRelocateRequest;
import com.eleven.hotel.api.endpoint.request.HotelUpdateRequest;
import com.eleven.hotel.application.command.HotelCloseCommand;
import com.eleven.hotel.application.command.HotelOpenCommand;
import com.eleven.hotel.application.command.HotelRelocateCommand;
import com.eleven.hotel.application.command.HotelUpdateCommand;
import com.eleven.hotel.application.convert.HotelConvertor;
import com.eleven.hotel.application.service.HotelService;
import com.eleven.hotel.domain.model.hotel.HotelDesc;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.values.Contact;
import com.eleven.hotel.domain.values.Position;
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
    private final HotelRepository hotelRepository;

    @Operation(summary = "read hotel")
    @GetMapping("/{hotelId}")
    public Optional<HotelDto> readHotel(@PathVariable("hotelId") String hotelId) {
        return hotelRepository.findById(hotelId).map(hotelConvertor.entities::toDto);

    }

    @Operation(summary = "update hotel")
    @PostMapping("/{hotelId}")
    public void update(@PathVariable("hotelId") String hotelId, @RequestBody @Validated HotelUpdateRequest request) {
        var command = HotelUpdateCommand.builder()
                .hotelId(hotelId)
                .desc(HotelDesc.builder()
                        .description(request.getDescription())
                        .roomNumber(request.getRoomNumber())
                        .headPicUrl(request.getHeadPicUrl())
                        .checkInTime(request.getCheckIn())
                        .checkOutTime(request.getCheckOut())
                        .build())
                .contact(Contact.of(request.getTel(), request.getEmail()))
                .build();
        hotelService.update(command);
    }

    @Operation(summary = "update hotel address")
    @PostMapping("/{hotelId}/address")
    public void relocate(@PathVariable("hotelId") String hotelId, @RequestBody @Validated HotelRelocateRequest request) {
        var command = HotelRelocateCommand.builder()
                .hotelId(hotelId)
                .position(Position.builder()
                        .province(request.getProvince())
                        .city(request.getCity())
                        .district(request.getDistrict())
                        .street(request.getStreet())
                        .address(request.getAddress())
                        .lng(request.getLng())
                        .lat(request.getLat())
                        .build())
                .build();
        hotelService.relocate(command);
    }

    @Operation(summary = "open hotel")
    @PostMapping("/{hotelId}/operations/open")
    public void openHotel(@PathVariable("hotelId") String hotelId) {
        var command = HotelOpenCommand.builder()
                .hotelId(hotelId)
                .build();
        hotelService.open(command);
    }

    @Operation(summary = "close hotel")
    @PostMapping("/{hotelId}/operations/close")
    public void closeHotel(@PathVariable("hotelId") String hotelId) {
        var command = HotelCloseCommand.builder()
                .hotelId(hotelId)
                .build();
        hotelService.close(command);
    }

}
