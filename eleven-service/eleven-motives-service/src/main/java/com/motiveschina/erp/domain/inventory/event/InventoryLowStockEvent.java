package com.motiveschina.erp.domain.inventory.event;

import com.motiveschina.core.layer.DomainEvent;
import com.motiveschina.erp.domain.inventory.Inventory;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class InventoryLowStockEvent implements DomainEvent {
    Inventory inventory;
    int quantity;
}
