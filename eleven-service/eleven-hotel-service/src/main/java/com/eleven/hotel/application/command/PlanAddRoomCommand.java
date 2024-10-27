package com.eleven.hotel.application.command;

import com.eleven.hotel.domain.values.Price;
import com.eleven.hotel.domain.values.Stock;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlanAddRoomCommand {
    private String hotelId;
    private String planId;

    private String roomId;
    private Stock stock;
    private Price price;
}
