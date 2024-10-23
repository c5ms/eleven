package com.eleven.hotel.application.command;

import com.eleven.hotel.domain.core.HotelAware;
import com.eleven.hotel.domain.model.hotel.HotelDesc;
import com.eleven.hotel.domain.values.Contact;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HotelUpdateCommand  {
    private String hotelId;
    private HotelDesc desc;
    private Contact contact;
}
