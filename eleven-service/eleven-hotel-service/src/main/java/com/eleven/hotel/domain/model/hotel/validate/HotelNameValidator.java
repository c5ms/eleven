package com.eleven.hotel.domain.model.hotel.validate;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.eleven.core.domain.DomainUtils;
import com.eleven.hotel.api.domain.core.HotelErrors;
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
        var existing = hotelRepository.getHotelByName(hotel.getName())
                .stream().filter(exist -> !StringUtils.equals(exist.getId(), hotel.getId()))
                .findFirst();
        DomainUtils.must(existing.isEmpty(), HotelErrors.HOTEL_NAME_REPEAT);
    }

}
