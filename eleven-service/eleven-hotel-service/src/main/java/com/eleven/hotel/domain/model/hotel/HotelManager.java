package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.data.SerialGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelManager {

    private final List<HotelValidator> hotelValidators;
    private final SerialGenerator serialGenerator;

    public void validate(Hotel hotel) {
        hotelValidators.forEach(validator -> validator.validate(hotel));
    }

    public Hotel create(Register register) {
        return Hotel.of(nextHotelId(), register);
    }

    public String nextHotelId() {
        return serialGenerator.nextString(Hotel.DOMAIN_NAME);
    }

}
