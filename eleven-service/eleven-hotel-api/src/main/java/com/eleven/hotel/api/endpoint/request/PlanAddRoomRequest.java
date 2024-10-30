package com.eleven.hotel.api.endpoint.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanAddRoomRequest {
    private String roomId;
    private Integer stock;
    private Double price;
}
