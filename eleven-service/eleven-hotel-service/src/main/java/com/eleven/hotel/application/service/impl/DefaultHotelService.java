package com.eleven.hotel.application.service.impl;

import com.eleven.core.application.ApplicationUtils;
import com.eleven.core.domain.DomainUtils;
import com.eleven.hotel.api.application.event.HotelUpdatedEvent;
import com.eleven.hotel.application.command.HotelCloseCommand;
import com.eleven.hotel.application.command.HotelOpenCommand;
import com.eleven.hotel.application.command.HotelRelocateCommand;
import com.eleven.hotel.application.command.HotelUpdateCommand;
import com.eleven.hotel.application.service.HotelService;
import com.eleven.hotel.domain.model.hotel.HotelManager;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
class DefaultHotelService implements HotelService {

    private final HotelManager hotelManager;
    private final HotelRepository hotelRepository;


    @Override
    public void update(HotelUpdateCommand command) {
        var hotel = hotelRepository.requireById(command.getHotelId());
        hotel.update(command.getDesc());
        hotel.update(command.getContact());
        hotelManager.validate(hotel);
        hotelRepository.save(hotel);
        ApplicationUtils.publishEvent(new HotelUpdatedEvent(hotel.getId()));
    }

    @Override
    public void relocate(HotelRelocateCommand command) {
        var hotel = hotelRepository.requireById(command.getHotelId());
        hotel.relocate(command.getPosition());
        hotelRepository.save(hotel);
    }

    @Override
    public void open(HotelOpenCommand command) {
        var hotel = hotelRepository.requireById(command.getHotelId());
        hotel.startSale();
        hotelRepository.save(hotel);
    }

    @Override
    public void close(HotelCloseCommand command) {
        var hotel = hotelRepository.requireById(command.getHotelId());
        hotel.stopSale();
        hotelRepository.save(hotel);
    }


}
