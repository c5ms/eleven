package com.eleven.hotel.domain.model.hotel.validate;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.eleven.core.domain.DomainUtils;
import com.eleven.hotel.api.domain.core.HotelErrors;
import com.eleven.hotel.domain.model.hotel.Room;
import com.eleven.hotel.domain.model.hotel.RoomRepository;
import com.eleven.hotel.domain.model.hotel.RoomValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoomNameValidator implements RoomValidator {
    private final RoomRepository roomRepository;

    @Override
    public void validate(Room room) {
        var existing = roomRepository.getRoomsByHotelIdAndName(room.getHotelId(), room.getName())
                .stream().filter(exist -> !StringUtils.equals(exist.getId(), room.getId()))
                .findFirst();
        DomainUtils.must(existing.isEmpty(), HotelErrors.ROOM_NAME_REPEAT);
    }

}
