package com.eleven.hotel.application.service;

import com.eleven.hotel.api.application.event.HotelCreatedEvent;
import com.eleven.hotel.application.command.HotelRegisterCommand;
import com.eleven.hotel.application.command.RegisterReviewCommand;
import com.eleven.hotel.application.support.HotelContext;
import com.eleven.hotel.domain.manager.RegisterManager;
import com.eleven.hotel.domain.model.hotel.*;
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
        registerRepository.persist(register);
        return register;
    }

    @Transactional(rollbackFor = Exception.class)
    public void review(Long registerId,RegisterReviewCommand command) {
        var register = registerRepository.findById(registerId).orElseThrow(HotelContext::noPrincipalException);
        if (command.isPass()) {

            var hotel = new Hotel(register);
            hotelRepository.persist(hotel);

            var description = new Admin.Description(
                    register.getAdmin().getName(),
                    register.getAdmin().getEmail(),
                    register.getAdmin().getTel()
            );
            var admin = new Admin(hotel.getHotelId(), description);
            adminRepository.persist(admin);

            register.accept();
            register.belongTo(hotel);
            registerRepository.persist(register);

            HotelContext.publishEvent(new HotelCreatedEvent(hotel.getHotelId()));
        } else {
            register.reject();
            registerRepository.persist(register);
        }
    }

}
