package com.eleven.hotel.application.service;

import com.eleven.core.application.ApplicationUtils;
import com.eleven.core.domain.DomainUtils;
import com.eleven.hotel.api.application.event.HotelCreatedEvent;
import com.eleven.hotel.api.application.event.HotelUpdatedEvent;
import com.eleven.hotel.application.command.*;
import com.eleven.hotel.domain.model.hotel.HotelManager;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.model.hotel.Register;
import com.eleven.hotel.domain.model.hotel.RegisterRepository;
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
    private final RegisterRepository registerRepository;

    public Register register(HotelRegisterCommand command) {
        var register = Register.create(DomainUtils.nextId(), command.getHotelName(), command.getHotelAddress(), command.getAdminContact());
        hotelManager.validate(register);
        registerRepository.save(register);
        return register;
    }

    public void review(RegisterReviewCommand command) {
        var register = registerRepository.requireById(command.getRegisterId());
        if (command.isPass()) {
            var hotel = hotelManager.accept(register);
            ApplicationUtils.publishEvent(new HotelCreatedEvent(hotel.getId()));
        } else {
            hotelManager.reject(register);
        }
    }

    public void update(HotelUpdateCommand command) {
        var hotel = hotelRepository.requireById(command.getHotelId());

        Optional.ofNullable(command.getPosition()).ifPresent(hotel::relocate);
        Optional.ofNullable(command.getDescription()).ifPresent(hotel::setDescription);

        hotelManager.validate(hotel);
        hotelRepository.save(hotel);
        ApplicationUtils.publishEvent(new HotelUpdatedEvent(hotel.getId()));
    }

    public void open(HotelOpenCommand command) {
        var hotel = hotelRepository.requireById(command.getHotelId());
        hotel.startSale();
        hotelRepository.save(hotel);
    }

    public void close(HotelCloseCommand command) {
        var hotel = hotelRepository.requireById(command.getHotelId());
        hotel.stopSale();
        hotelRepository.save(hotel);
    }


}
