package com.eleven.hotel.application.service;

import com.eleven.core.application.ApplicationHelper;
import com.eleven.hotel.api.application.event.HotelUpdatedEvent;
import com.eleven.hotel.application.command.*;
import com.eleven.hotel.domain.model.hotel.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class HotelCommandService {
    private final HotelManager hotelManager;
    private final HotelRepository hotelRepository;

    public void update(HotelUpdateCommand command) {
        var hotel = hotelRepository.require(command.getHotelId());

        Optional.ofNullable(command.getPosition()).ifPresent(hotel::relocate);
        Optional.ofNullable(command.getDescription()).ifPresent(hotel::setDescription);

        hotelManager.validate(hotel);
        hotelRepository.save(hotel);
        ApplicationHelper.publishEvent(new HotelUpdatedEvent(hotel.getId()));
    }

    public void open(HotelOpenCommand command) {
        var hotel = hotelRepository.require(command.getHotelId());
        hotel.startSale();
        hotelRepository.save(hotel);
    }

    public void close(HotelCloseCommand command) {
        var hotel = hotelRepository.require(command.getHotelId());
        hotel.stopSale();
        hotelRepository.save(hotel);
    }


}
