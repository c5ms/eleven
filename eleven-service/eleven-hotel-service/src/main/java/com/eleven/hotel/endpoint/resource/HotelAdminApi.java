package com.eleven.hotel.endpoint.resource;

import com.eleven.core.model.PageResult;
import com.eleven.core.web.annonation.AsAdminApi;
import com.eleven.hotel.api.application.view.HotelDto;
import com.eleven.hotel.api.endpoint.core.HotelEndpoints;
import com.eleven.hotel.api.endpoint.request.HotelQueryRequest;
import com.eleven.hotel.application.query.HotelQuery;
import com.eleven.hotel.application.convert.HotelConvertor;
import com.eleven.hotel.application.query.HotelQueryService;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@AsAdminApi
@RequiredArgsConstructor
@Tag(name = HotelEndpoints.Tags.HOTEL)
@RequestMapping(HotelEndpoints.Paths.HOTEL)
public class HotelAdminApi {

    private final HotelConvertor hotelConvertor;
    private final HotelRepository hotelRepository;
    private final HotelQueryService hotelQueryService;

    @Operation(summary = "read hotel")
    @GetMapping("/{hotelId}")
    public Optional<HotelDto> readHotel(@PathVariable("hotelId") String hotelId) {
        return hotelRepository.findById(hotelId).map(hotelConvertor.entities::toDto);
    }

    @Operation(summary = "query hotel")
    @GetMapping
    public PageResult<HotelDto> queryHotel(@ParameterObject @Validated HotelQueryRequest request) {
        var command = HotelQuery.builder()
                .hotelName(request.getHotelName())
                .build();
        return hotelQueryService.queryPage(command).map(hotelConvertor.entities::toDto);
    }

}
