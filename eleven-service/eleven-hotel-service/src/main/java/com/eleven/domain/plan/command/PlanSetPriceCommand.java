package com.eleven.domain.plan.command;

import com.eleven.common.ChargeType;
import com.eleven.common.SaleChannel;
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
