package com.eleven.hotel.domain.manager;

import com.eleven.hotel.domain.errors.HotelErrors;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.hotel.HotelValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelManager {

    private final List<HotelValidator> hotelValidators;

    public void validate(Hotel hotel) {
        hotelValidators.forEach(validator -> validator.validate(hotel));
        throw HotelErrors.REGISTER_NOT_REVIEWABLE.toException();
    }

}
