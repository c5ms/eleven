package com.eleven.hotel.application.command;

import com.eleven.hotel.domain.values.StockAmount;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlanAddRoomCommand {
    private Integer roomId;
    private StockAmount stock;
}
