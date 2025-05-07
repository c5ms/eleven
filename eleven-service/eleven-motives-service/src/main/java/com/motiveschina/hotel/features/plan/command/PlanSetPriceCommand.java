package com.motiveschina.hotel.features.plan.command;

import java.math.BigDecimal;
import com.motiveschina.hotel.core.ChargeType;
import com.motiveschina.hotel.core.SaleChannel;
import lombok.Builder;
import lombok.Getter;

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
