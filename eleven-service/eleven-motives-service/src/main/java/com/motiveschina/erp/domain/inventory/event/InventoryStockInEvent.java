package com.motiveschina.erp.domain.inventory.event;

import com.motiveschina.erp.domain.inventory.Inventory;
import com.motiveschina.core.layer.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Value;


@Value
@AllArgsConstructor(staticName = "of")
public class InventoryStockInEvent implements DomainEvent {
    Inventory inventory;
    int quantity;
}

