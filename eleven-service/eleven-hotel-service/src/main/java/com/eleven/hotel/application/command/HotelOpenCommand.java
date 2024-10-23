package com.eleven.hotel.application.command;

import com.eleven.hotel.domain.core.HotelAware;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HotelOpenCommand  {
    private String hotelId;
}
