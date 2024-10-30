package com.eleven.hotel.application.service;

import com.eleven.core.application.ApplicationHelper;
import com.eleven.hotel.api.application.event.HotelCreatedEvent;
import com.eleven.hotel.application.command.HotelRegisterCommand;
import com.eleven.hotel.application.command.RegisterReviewCommand;
import com.eleven.hotel.domain.manager.AdminManager;
import com.eleven.hotel.domain.manager.RegisterManager;
import com.eleven.hotel.domain.model.hotel.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class RegisterService {

    private final RegisterManager registerManager;
    private final RegisterRepository registerRepository;

    private final HotelManager hotelManager;
    private final HotelRepository hotelRepository;

    private final AdminManager adminManager;
    private final AdminRepository adminRepository;

    public Register register(HotelRegisterCommand command) {
        var register = registerManager.create(command.getHotel(), command.getAdmin());
        registerRepository.save(register);
        return register;
    }

    public void review(RegisterReviewCommand command)  {
        var register = registerRepository.require(command.getRegisterId());
        if (command.isPass()) {

            var hotel = hotelManager.create(register);
            hotelRepository.save(hotel);

            var admin = adminManager.create(hotel, register);
            adminRepository.save(admin);

            register.accept();
            register.belongTo(hotel);
            registerRepository.save(register);

            ApplicationHelper.publishEvent(new HotelCreatedEvent(hotel.getId()));
        } else {
            register.reject();
            registerRepository.save(register);
        }
    }

}
