package com.motiveschina.erp.application.command;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InventoryStockInCommand {
    Long purchaseOrderId;
}
