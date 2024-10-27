package com.eleven.hotel.domain.model.hotel;

import com.eleven.core.data.SerialGenerator;
import com.eleven.hotel.api.domain.model.ChargeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomManager {
    private final List<RoomValidator> roomValidators;
    private final SerialGenerator serialGenerator;

    public String nextRoomId(String hotelId) {
        return serialGenerator.nextString(Room.DOMAIN_NAME, Hotel.DOMAIN_NAME, hotelId);
    }

    public void validate(Room room) {
        roomValidators.forEach(roomValidator -> roomValidator.validate(room));
    }

    public Room create(Hotel hotel, Room.Description description, Room.Restriction restriction, ChargeType chargeType) {
        var room = Room.of(hotel.getHotelId(),
            nextRoomId(hotel.getHotelId()),
            description,
            restriction,
            chargeType
        );
        validate(room);
        return room;
    }
}
