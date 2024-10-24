package com.eleven.hotel.application.command;

import com.eleven.hotel.domain.model.hotel.Admin;
import com.eleven.hotel.domain.model.hotel.Desc;
import com.eleven.hotel.domain.model.hotel.Hotel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HotelUpdateCommand  {
    private String hotelId;
    private Desc desc;
    private Hotel.Contact contact;
}
