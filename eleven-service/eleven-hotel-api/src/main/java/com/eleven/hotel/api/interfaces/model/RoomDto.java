package com.eleven.hotel.api.interfaces.model;

import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.RoomLevel;
import com.eleven.hotel.api.domain.model.SaleState;
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
}
