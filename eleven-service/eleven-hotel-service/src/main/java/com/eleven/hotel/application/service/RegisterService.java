package com.eleven.hotel.application.service;

import com.eleven.hotel.application.command.HotelRegisterCommand;
import com.eleven.hotel.application.command.RegisterReviewCommand;
import com.eleven.hotel.domain.model.hotel.Register;

public interface RegisterService {

    Register register(HotelRegisterCommand command);

    void review(RegisterReviewCommand command);
}
