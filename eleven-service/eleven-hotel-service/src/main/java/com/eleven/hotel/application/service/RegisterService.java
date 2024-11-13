package com.eleven.hotel.application.service;

import com.eleven.core.domain.DomainEvents;
import com.eleven.hotel.application.command.HotelRegisterCommand;
import com.eleven.hotel.application.command.RegisterReviewCommand;
import com.eleven.hotel.application.support.HotelContext;
import com.eleven.hotel.domain.manager.RegisterManager;
import com.eleven.hotel.domain.model.admin.Admin;
import com.eleven.hotel.domain.model.admin.AdminRepository;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.hotel.HotelCreatedEvent;
import com.eleven.hotel.domain.model.hotel.HotelRepository;
import com.eleven.hotel.domain.model.register.Register;
import com.eleven.hotel.domain.model.register.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final HotelRepository hotelRepository;
    private final AdminRepository adminRepository;
    private final RegisterRepository registerRepository;

    private final RegisterManager registerManager;

    @Transactional(rollbackFor = Exception.class)
    public Register register(HotelRegisterCommand command) {
        var register = new Register(command.getHotel(), command.getAdmin());
        registerManager.validate(register);
        registerRepository.saveAndFlush(register);
        return register;
    }

    @Transactional(rollbackFor = Exception.class)
    public void review(Long registerId, RegisterReviewCommand command) {
        var register = registerRepository.findById(registerId).orElseThrow(HotelContext::noPrincipalException);
        if (command.isPass()) {
            register.accept();

            var hotel = Hotel.of(register);
            register.belongTo(hotel);

            var admin = new Admin(hotel.getHotelId(), new Admin.Description(
                    register.getAdmin().getName(),
                    register.getAdmin().getEmail(),
                    register.getAdmin().getTel()
            ));

            adminRepository.saveAndFlush(admin);
            hotelRepository.saveAndFlush(hotel);

            DomainEvents.publish(HotelCreatedEvent.of(hotel));
        } else {
            register.reject();
        }

        registerRepository.saveAndFlush(register);
    }
}
