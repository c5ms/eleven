package com.eleven.hotel.endpoint.web.inner;

import com.eleven.core.web.annonation.AsInternalApi;
import com.eleven.hotel.api.endpoint.core.HotelEndpoints;
import com.eleven.hotel.api.endpoint.internal.HotelClient;
import com.eleven.hotel.api.application.model.HotelDto;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.application.convert.HotelConvertor;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Slf4j
@AsInternalApi
@Tag(name = HotelEndpoints.Tags.HOTEL)
@RequiredArgsConstructor
public class HotelInnerApi implements HotelClient {

    private final HotelConvertor hotelConvertor;
    private final HotelRepository hotelRepository;

    @Override
    public Optional<HotelDto> readHotel(@RequestParam("id") Long id) {
        return hotelRepository.findById(id).map(hotelConvertor::toDto);
    }
}
