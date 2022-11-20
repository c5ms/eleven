package com.eleven.hotel.endpoint.resource;

import com.eleven.core.web.annonation.AsInternalApi;
import com.eleven.hotel.api.application.model.HotelDto;
import com.eleven.hotel.api.endpoint.internal.HotelClient;
import com.eleven.hotel.application.convert.HotelConvertor;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Slf4j
@AsInternalApi
@Tag(name = "hotel")
@RequiredArgsConstructor
public class HotelInnerApi implements HotelClient {

    private final HotelConvertor hotelConvertor;
    private final HotelRepository hotelRepository;

    @Override
    public Optional<HotelDto> readHotel(@RequestParam("id") String id) {
        return hotelRepository.findById(id).map(hotelConvertor.entities::toDto);
    }
}
