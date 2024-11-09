package com.eleven.hotel.application.command;

import com.eleven.hotel.api.domain.model.ChargeType;
import com.eleven.hotel.api.domain.model.SaleChannel;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class PlanSetPriceCommand {
    private SaleChannel saleChannel;
    private ChargeType chargeType;

    private BigDecimal wholeRoomPrice;
    private BigDecimal onePersonPrice;
    private BigDecimal twoPersonPrice;
    private BigDecimal threePersonPrice;
    private BigDecimal fourPersonPrice;
    private BigDecimal fivePersonPrice;
    
}
