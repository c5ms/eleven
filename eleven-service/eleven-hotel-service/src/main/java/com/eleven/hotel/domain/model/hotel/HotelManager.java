package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.domain.DomainUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelManager {

    private final List<HotelRoomValidator> hotelRoomValidators;
    private final List<HotelValidator> hotelValidators;

    private final HotelRepository hotelRepository;
    private final AdminRepository adminRepository;
    private final RegisterRepository registerRepository;

    public String nextRoomId() {
        return DomainUtils.nextId();
    }

    public Hotel accept(Register register) {
        var hotel = Hotel.create(DomainUtils.nextId(), register);
        hotelRepository.save(hotel);

        var admin = Admin.create(DomainUtils.nextId(), hotel, register.getManagerContact());
        adminRepository.save(admin);

        register.accept();
        register.belongTo(hotel);
        registerRepository.save(register);

        return hotel;
    }

    public void reject(Register register) {
        register.reject();
        registerRepository.save(register);
    }

    public void validate(Register register) {
        var hotel = Hotel.create(null, register);
        validate(hotel);
    }

    public void validate(Hotel hotel) {
        hotelValidators.forEach(validator -> validator.validate(hotel));
    }

    public void validate(HotelRoom hotelRoom) {
        hotelRoomValidators.forEach(hotelRoomValidator -> hotelRoomValidator.validate(hotelRoom));
    }


}
