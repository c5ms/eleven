package com.eleven.hotel.domain.manager;

import com.eleven.core.data.SerialGenerator;
import com.eleven.hotel.domain.model.hotel.Register;
import com.eleven.hotel.domain.model.hotel.RegisterValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegisterManager {

    private final List<RegisterValidator> registerValidators;
    private final SerialGenerator serialGenerator;

    private String nextId() {
        return serialGenerator.nextString(Register.DOMAIN_NAME);
    }

    public Register create(Register.HotelInformation hotel, Register.AdminInformation admin) {
        var register = Register.of(nextId(), hotel, admin);
        validate(register);
        return register;
    }

    private void validate(Register register) {
        registerValidators.forEach(roomValidator -> roomValidator.validate(register));
    }
}
