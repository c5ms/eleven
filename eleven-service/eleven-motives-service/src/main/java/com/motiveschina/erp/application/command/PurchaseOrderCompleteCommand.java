package com.motiveschina.erp.application.command;


import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class PurchaseOrderCompleteCommand {

    Long orderId;

    Boolean pass;

    LocalDate stockDate;
}
