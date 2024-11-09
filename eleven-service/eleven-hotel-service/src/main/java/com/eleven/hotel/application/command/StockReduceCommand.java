package com.eleven.hotel.application.command;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class StockReduceCommand {
    private LocalDate date;
    private int amount;
}
