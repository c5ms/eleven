package com.eleven.hotel.application.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StockReduceCommand {
  private   int amount;
}
