package com.eleven.hotel.api.interfaces.model;

import com.eleven.core.domain.values.DateRange;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Schema(name = "Room")
@Accessors(chain = true)
public class RoomDto {
    private Long hotelId;
    private Long roomId;
    private String name;
    private RoomLevel type;
    private String headPicUrl;
    private String desc;
    private Integer maxPerson;
    private Integer minPerson;

    private Integer count;

    private DateRange stayPeriod;
}
