package com.eleven.hotel.application.command;

import com.eleven.hotel.domain.model.hotel.Hotel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HotelRelocateCommand {
    private String hotelId;
    private Hotel.Position position;
}
