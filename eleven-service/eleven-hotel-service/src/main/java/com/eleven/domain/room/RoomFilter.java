package com.eleven.domain.room;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomFilter {
    private Long hotelId;
    private String planName;
}
