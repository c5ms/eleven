package com.eleven.hotel.api.endpoint.model;

import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.RoomType;
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
    private Integer hotelId;
    private Integer roomId;
    private String name;
    private RoomType type;
    private SaleState saleState;
    private ChargeType chargeType;
    private String headPicUrl;
    private String desc;
    private Integer maxPerson;
    private Integer minPerson;
}
