package com.eleven.hotel.domain.manager;

import com.eleven.hotel.domain.model.hotel.*;
import com.eleven.hotel.domain.model.inventory.RoomInventoryRepository;
import com.eleven.hotel.domain.model.room.Room;
import com.eleven.hotel.domain.model.room.RoomValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelManager {
    private final List<RoomValidator> roomValidators;
    private final List<HotelValidator> hotelValidators;
    private final RoomInventoryRepository roomInventoryRepository;

    public void validate(Hotel hotel) {
        hotelValidators.forEach(validator -> validator.validate(hotel));
    }

    public void validate(Room room) {
        roomValidators.forEach(roomValidator -> roomValidator.validate(room));
    }


}
