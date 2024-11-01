package com.eleven.hotel.application.command;

import com.eleven.hotel.domain.values.Price;
import com.eleven.hotel.domain.values.Stock;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlanAddRoomCommand {
    private Integer roomId;
    private Stock stock;
}
