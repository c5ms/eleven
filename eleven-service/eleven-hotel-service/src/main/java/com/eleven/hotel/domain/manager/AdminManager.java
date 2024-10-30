package com.eleven.hotel.domain.manager;

import com.eleven.core.data.SerialGenerator;
import com.eleven.hotel.domain.model.hotel.Admin;
import com.eleven.hotel.domain.model.hotel.Hotel;
import com.eleven.hotel.domain.model.hotel.Register;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminManager {

    private final SerialGenerator serialGenerator;

    public String nextId() {
        return serialGenerator.nextString(Hotel.DOMAIN_NAME);
    }

    public Admin create(Hotel hotel, Register register) {
        var description = new Admin.Description(
            register.getAdmin().getName(),
            register.getAdmin().getEmail(),
            register.getAdmin().getTel()
        );
        return Admin.of(nextId(), hotel.getHotelId(), description);
    }

}
