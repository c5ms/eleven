package com.eleven.hotel.application.command;

import com.eleven.hotel.domain.core.HotelAware;
import com.eleven.hotel.domain.values.Position;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HotelRelocateCommand  {
    private String hotelId;
    private Position position;
}
