package com.eleven.hotel.domain.model.hotel.validate;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.eleven.core.domain.DomainUtils;
import com.eleven.hotel.api.domain.core.HotelErrors;
import com.eleven.hotel.domain.model.hotel.HotelRoom;
import com.eleven.hotel.domain.model.hotel.HotelRoomRepository;
import com.eleven.hotel.domain.model.hotel.HotelRoomValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HotelRoomNameValidator implements HotelRoomValidator {
    private final HotelRoomRepository hotelRoomRepository;

    @Override
    public void validate(HotelRoom hotelRoom) {
        var existing = hotelRoomRepository.getRoomsByHotelIdAndName(hotelRoom.getHotelId(), hotelRoom.getName())
                .stream().filter(exist -> !StringUtils.equals(exist.getId(), hotelRoom.getId()))
                .findFirst();
        DomainUtils.must(existing.isEmpty(), HotelErrors.ROOM_NAME_REPEAT);
    }

}
