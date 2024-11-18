package com.eleven.hotel.application.query.filter;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomFilter {
    private Long hotelId;
    private String planName;
}
