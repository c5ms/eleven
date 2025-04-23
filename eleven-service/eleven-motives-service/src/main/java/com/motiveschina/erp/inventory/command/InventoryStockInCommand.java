package com.motiveschina.erp.inventory.command;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InventoryStockInCommand {
     Long purchaseOrderId;
}
