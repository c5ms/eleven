package com.eleven.hotel.api.endpoint.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanAddRoomRequest {
    private Integer roomId;
    private Integer stock;
    private Double price;
}
