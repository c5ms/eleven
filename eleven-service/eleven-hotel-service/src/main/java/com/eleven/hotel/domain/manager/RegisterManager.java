package com.eleven.hotel.domain.manager;

import com.eleven.hotel.domain.model.register.Register;
import com.eleven.hotel.domain.model.register.RegisterValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegisterManager {

    private final List<RegisterValidator> registerValidators;

    public void validate(Register register) {
        registerValidators.forEach(roomValidator -> roomValidator.validate(register));
    }


}
