package com.eleven.hotel.api.endpoint.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanAddRoomRequest {
    private Long roomId;
    private Integer stock;
    private Double price;
}
