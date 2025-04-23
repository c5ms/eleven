package com.motiveschina.erp.inventory.event;

import com.motiveschina.erp.inventory.Inventory;
import com.motiveschina.erp.support.layer.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Value;


@Value
@AllArgsConstructor(staticName = "of")
public class InventoryStockInEvent implements DomainEvent {
    Inventory inventory;
    int quantity;
}

