package com.eleven.hotel.application.service;

import com.eleven.core.application.query.PageResult;
import com.eleven.hotel.api.application.event.HotelCreatedEvent;
import com.eleven.hotel.api.application.event.HotelUpdatedEvent;
import com.eleven.hotel.application.command.HotelCreateCommand;
import com.eleven.hotel.application.command.HotelUpdateCommand;
import com.eleven.hotel.application.query.HotelQuery;
import com.eleven.hotel.application.support.HotelContext;
import com.eleven.hotel.domain.manager.HotelManager;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
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
    public Optional<Hotel> read(Integer hotelId) {
        return hotelRepository.findById(hotelId);
    }

    public Hotel create(HotelCreateCommand command) {
        var hotel = new Hotel(command.getBasic(), command.getPosition());
        hotelManager.validate(hotel);
        hotelRepository.persist(hotel);
        HotelContext.publishEvent(HotelCreatedEvent.of(hotel.getHotelId()));
        return hotel;
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(Integer hotelId, HotelUpdateCommand command) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(HotelContext::noPrincipalException);

        Optional.ofNullable(command.getBasic()).ifPresent(hotel::setBasic);
        Optional.ofNullable(command.getPosition()).ifPresent(hotel::relocate);

        hotelManager.validate(hotel);
        hotelRepository.update(hotel);
        HotelContext.publishEvent(HotelUpdatedEvent.of(hotel.getHotelId()));
    }

    @Transactional(rollbackFor = Exception.class)
    public void open(Integer hotelId) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(HotelContext::noPrincipalException);
        hotel.startSale();
        hotelRepository.update(hotel);
    }

    @Transactional(rollbackFor = Exception.class)
    public void close(Integer hotelId) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(HotelContext::noPrincipalException);
        hotel.stopSale();
        hotelRepository.update(hotel);
    }


}
