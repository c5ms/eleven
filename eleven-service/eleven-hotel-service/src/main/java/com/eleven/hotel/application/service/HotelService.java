package com.eleven.hotel.application.service;

import com.eleven.core.domain.event.DomainEvents;
import com.eleven.hotel.application.command.HotelCreateCommand;
import com.eleven.hotel.application.command.HotelUpdateCommand;
import com.eleven.hotel.application.support.HotelContext;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.model.hotel.HotelUpdatedEvent;
import com.eleven.hotel.domain.service.HotelManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class HotelService {

    private final HotelManager hotelManager;
    private final HotelRepository hotelRepository;

    public Hotel create(HotelCreateCommand command) {
        var hotel = Hotel.builder()
            .basic(command.getBasic())
            .checkPolicy(command.getCheckPolicy())
            .build();
        hotelManager.validate(hotel);
        hotelRepository.saveAndFlush(hotel);
        return hotel;
    }

    public void update(Long hotelId, HotelUpdateCommand command) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(HotelContext::noPrincipalException);
// todo update hotel
        hotelManager.validate(hotel);
        hotelRepository.saveAndFlush(hotel);
        DomainEvents.publish(HotelUpdatedEvent.of(hotel));
    }

    public void open(Long hotelId) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(HotelContext::noPrincipalException);
        hotel.active();
        hotelRepository.saveAndFlush(hotel);
    }

    public void close(Long hotelId) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(HotelContext::noPrincipalException);
        hotel.deactivate();
        hotelRepository.saveAndFlush(hotel);
    }


}
