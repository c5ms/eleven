package com.eleven.hotel.application.service;

import com.eleven.hotel.api.application.event.HotelCreatedEvent;
import com.eleven.hotel.api.domain.model.RegisterState;
import com.eleven.hotel.application.command.HotelRegisterCommand;
import com.eleven.hotel.application.command.RegisterReviewCommand;
import com.eleven.hotel.application.service.manager.HotelManager;
import com.eleven.hotel.application.service.manager.RegisterManager;
import com.eleven.hotel.application.support.HotelContext;
import com.eleven.hotel.domain.model.hotel.AdminRepository;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.model.hotel.Register;
import com.eleven.hotel.domain.model.hotel.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final HotelRepository hotelRepository;
    private final AdminRepository adminRepository;
    private final RegisterRepository registerRepository;

    private final HotelManager hotelManager;
    private final RegisterManager registerManager;

    @Transactional(rollbackFor = Exception.class)
    public Register register(HotelRegisterCommand command) {
        var register = registerManager.create(command);
        registerManager.validate(register);
        registerRepository.persistAndFlush(register);
        return register;
    }

    @Transactional(rollbackFor = Exception.class)
    public void review(Long registerId, RegisterReviewCommand command) {
        var register = registerRepository.findById(registerId).orElseThrow(HotelContext::noPrincipalException);
        registerManager.review(register, command);

        if (register.getState() == RegisterState.ACCEPTED) {
            var hotel = hotelManager.createHotel(register);
            hotelRepository.persist(hotel);

            var admin = hotelManager.createAdmin(hotel, register);
            adminRepository.persist(admin);

            HotelContext.publishEvent(new HotelCreatedEvent(hotel.getHotelId()));
        }

        registerRepository.persistAndFlush(register);
    }
}
