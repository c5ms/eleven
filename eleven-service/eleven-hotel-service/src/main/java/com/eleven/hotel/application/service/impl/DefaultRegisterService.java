package com.eleven.hotel.application.service.impl;

import com.eleven.core.application.ApplicationUtils;
import com.eleven.core.domain.DomainUtils;
import com.eleven.hotel.api.application.event.HotelCreatedEvent;
import com.eleven.hotel.application.command.HotelRegisterCommand;
import com.eleven.hotel.application.command.RegisterReviewCommand;
import com.eleven.hotel.application.service.RegisterService;
import com.eleven.hotel.domain.model.hotel.HotelManager;
import com.eleven.hotel.domain.model.hotel.Register;
import com.eleven.hotel.domain.model.hotel.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
class DefaultRegisterService implements RegisterService {

    private final HotelManager hotelManager;
    private final RegisterRepository registerRepository;

    @Override
    public Register register(HotelRegisterCommand command) {
        var register = Register.create(DomainUtils.nextId(), command.getHotelName(), command.getHotelAddress(), command.getAdminContact());
        hotelManager.validate(register);
        registerRepository.save(register);
        return register;
    }

    @Override
    public void review(RegisterReviewCommand command) {
        var register = registerRepository.requireById(command.getRegisterId());
        if (command.isPass()) {
            var hotel = hotelManager.accept(register);
            ApplicationUtils.publishEvent(new HotelCreatedEvent(hotel.getId()));
        } else {
            hotelManager.reject(register);
        }
    }

}
