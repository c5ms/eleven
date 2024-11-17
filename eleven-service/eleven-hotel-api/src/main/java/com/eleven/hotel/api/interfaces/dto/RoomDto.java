package com.eleven.hotel.api.interfaces.dto;

import com.eleven.hotel.api.interfaces.vo.DateRangeVo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
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
    private Long hotelId;
    private Long roomId;
    private String name;
    private String desc;
    private Integer area;
    private Integer floor;
    private Integer maxPerson;
    private Integer minPerson;
    private Integer quantity;
    private DateRangeVo availablePeriod;
    private Set<String> images = new HashSet<>();
}
