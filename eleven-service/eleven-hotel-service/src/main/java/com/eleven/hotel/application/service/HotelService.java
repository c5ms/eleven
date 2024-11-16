package com.eleven.hotel.application.service;

import com.eleven.core.application.query.PageResult;
import com.eleven.core.domain.event.DomainEvents;
import com.eleven.hotel.application.command.HotelCreateCommand;
import com.eleven.hotel.application.command.HotelQuery;
import com.eleven.hotel.application.command.HotelUpdateCommand;
import com.eleven.hotel.application.support.HotelContext;
import com.eleven.hotel.domain.manager.HotelManager;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.model.hotel.HotelUpdatedEvent;
import com.github.wenhao.jpa.Specifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelManager hotelManager;
    private final HotelRepository hotelRepository;

    @Transactional(readOnly = true)
    public PageResult<Hotel> query(HotelQuery query) {
        var specification = Specifications.<Hotel>and()
            .build();
        var result = hotelRepository.findAll(specification, PageRequest.of(query.getPage(), query.getSize() - 1));
        return PageResult.of(result.getContent(), result.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Optional<Hotel> read(Long hotelId) {
        return hotelRepository.findById(hotelId);
    }

    public Hotel create(HotelCreateCommand command) {
        var hotel = Hotel.builder()
            .basic(command.getBasic())
            .position(command.getPosition())
            .address(command.getAddress())
            .checkPolicy(command.getCheckPolicy())
            .build();
        hotelManager.validate(hotel);
        hotelRepository.saveAndFlush(hotel);
        return hotel;
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(Long hotelId, HotelUpdateCommand command) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(HotelContext::noPrincipalException);
        Optional.ofNullable(command.getBasic()).ifPresent(hotel::setBasic);
        Optional.ofNullable(command.getPosition()).ifPresent(hotel::setPosition);
        hotelManager.validate(hotel);
        hotelRepository.saveAndFlush(hotel);
        DomainEvents.publish(HotelUpdatedEvent.of(hotel));
    }

    @Transactional(rollbackFor = Exception.class)
    public void open(Long hotelId) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(HotelContext::noPrincipalException);
        hotel.active();
        hotelRepository.saveAndFlush(hotel);
    }

    @Transactional(rollbackFor = Exception.class)
    public void close(Long hotelId) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(HotelContext::noPrincipalException);
        hotel.deactivate();
        hotelRepository.saveAndFlush(hotel);
    }


}
