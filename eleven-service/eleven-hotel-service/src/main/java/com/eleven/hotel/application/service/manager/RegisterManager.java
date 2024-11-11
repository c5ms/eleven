package com.eleven.hotel.application.service.manager;

import com.eleven.hotel.application.command.HotelRegisterCommand;
import com.eleven.hotel.application.command.RegisterReviewCommand;
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

    public void validate(Register register) {
        registerValidators.forEach(roomValidator -> roomValidator.validate(register));
    }

    public Register create(HotelRegisterCommand command) {
        return new Register(command.getHotel(), command.getAdmin());
    }

    public void review(Register register, RegisterReviewCommand command) {
        if (command.isPass()) {
            register.accept();
        } else {
            register.reject();
        }
    }

}
