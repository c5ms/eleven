package com.eleven.hotel.application.command;

import com.eleven.hotel.domain.model.hotel.HotelBasic;
import com.eleven.hotel.domain.model.hotel.HotelPosition;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HotelUpdateCommand {
    private HotelBasic basic;
    private HotelPosition position;
}
