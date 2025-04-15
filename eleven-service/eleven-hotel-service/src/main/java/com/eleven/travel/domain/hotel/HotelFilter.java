package com.eleven.travel.domain.hotel;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class HotelFilter {
    private String hotelName;
}
