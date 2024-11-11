package com.eleven.hotel.application.service.manager;

import com.eleven.hotel.application.command.HotelCreateCommand;
import com.eleven.hotel.application.command.HotelUpdateCommand;
import com.eleven.hotel.domain.model.hotel.Admin;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.hotel.HotelValidator;
import com.eleven.hotel.domain.model.hotel.Register;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelManager {

    private final List<HotelValidator> hotelValidators;

    public void validate(Hotel hotel) {
        hotelValidators.forEach(validator -> validator.validate(hotel));
    }

    public Hotel createHotel(HotelCreateCommand command) {
        return new Hotel(command.getBasic(), command.getPosition());
    }

    public Hotel createHotel(Register register) {
        var hotel = new Hotel(register);
        register.belongTo(hotel);
        return hotel;
    }

    public Admin createAdmin(Hotel hotel, Register register) {
        var description = new Admin.Description(
                register.getAdmin().getName(),
                register.getAdmin().getEmail(),
                register.getAdmin().getTel()
        );
        return new Admin(hotel.getHotelId(), description);
    }

    public void updateHotel(Hotel hotel, HotelUpdateCommand command) {
        Optional.ofNullable(command.getBasic()).ifPresent(hotel::setBasic);
        Optional.ofNullable(command.getPosition()).ifPresent(hotel::relocate);
    }


}
