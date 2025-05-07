package com.motiveschina.hotel.features.hotel;

import com.motiveschina.hotel.core.support.ContextSupport;
import com.motiveschina.hotel.features.hotel.command.HotelCreateCommand;
import com.motiveschina.hotel.features.hotel.command.HotelUpdateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class HotelService {

    private final HotelManager hotelManager;
    private final HotelRepository hotelRepository;

    public Hotel create(HotelCreateCommand command) {
        var hotel = Hotel.builder()
            .basic(command.getBasic())
            .address(command.getAddress())
            .checkPolicy(command.getCheckPolicy())
            .position(command.getPosition())
            .checkPolicy(command.getCheckPolicy())
            .build();
        hotelManager.validate(hotel);
        hotelRepository.saveAndFlush(hotel);
        return hotel;
    }

    public Hotel update(Long hotelId, HotelUpdateCommand command) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(ContextSupport::noPrincipalException);
        hotel.update(command);
        hotelManager.validate(hotel);
        hotelRepository.saveAndFlush(hotel);
        return hotel;
    }

    public void open(Long hotelId) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(ContextSupport::noPrincipalException);
        hotel.active();
        hotelRepository.saveAndFlush(hotel);
    }

    public void close(Long hotelId) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(ContextSupport::noPrincipalException);

        hotel.deactivate();
        hotelRepository.saveAndFlush(hotel);
    }


}
