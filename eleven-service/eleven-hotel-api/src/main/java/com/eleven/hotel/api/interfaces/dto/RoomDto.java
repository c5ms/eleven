package com.eleven.hotel.api.interfaces.dto;

import com.eleven.hotel.api.interfaces.vo.DateRangeVo;
import com.eleven.hotel.api.interfaces.vo.RoomBasicVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Schema(name = "Room")
@Accessors(chain = true)
public class RoomDto {
    private Long roomId;
    private Integer quantity;
    private DateRangeVo availablePeriod;
    private RoomBasicVo basic;
    private Set<String> images = new HashSet<>();
}
