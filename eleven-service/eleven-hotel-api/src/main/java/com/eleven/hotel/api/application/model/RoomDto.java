package com.eleven.hotel.api.application.model;

import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.RoomSize;
import com.eleven.hotel.api.domain.model.SaleState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Schema(name = "RoomDetail")
@Accessors(chain = true)
public class RoomDto {
    private String id;
    private String name;
    private Integer amount;
    private RoomSize size;
    private SaleState saleState;
    private ChargeType chargeType;
    private String headPicUrl;
    private String desc;
}
