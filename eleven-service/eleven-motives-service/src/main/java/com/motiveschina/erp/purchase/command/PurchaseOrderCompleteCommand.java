package com.motiveschina.erp.purchase.command;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PurchaseOrderCompleteCommand {

    Long orderId;

    Boolean pass;

}
