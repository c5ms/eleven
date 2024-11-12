package com.eleven.hotel.domain.manager;

import com.eleven.hotel.domain.model.hotel.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegisterManager {

    private final List<RegisterValidator> registerValidators;
    private final HotelRepository hotelRepository;
    private final AdminRepository adminRepository;
    private final RegisterRepository registerRepository;

    public void validate(Register register) {
        registerValidators.forEach(roomValidator -> roomValidator.validate(register));
    }

    public void accept(Register register) {

    }

    public void reject(Register register) {
        register.reject();
    }

}
