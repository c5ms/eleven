package com.eleven.hotel.domain.model.hotel.validate;

import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.model.hotel.HotelValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelNameValidator implements HotelValidator {

    private final HotelRepository hotelRepository;

    @Override
    public void validate(Hotel hotel) {
//        var existing = hotelRepository.getHotelByName(hotel.getDescription().getName())
//            .stream().filter(exist -> !StringUtils.equals(exist.getId(), hotel.getId()))
//            .findFirst();
//        DomainHelper.must(existing.isEmpty(), HotelErrors.HOTEL_NAME_REPEAT);
    }

}
