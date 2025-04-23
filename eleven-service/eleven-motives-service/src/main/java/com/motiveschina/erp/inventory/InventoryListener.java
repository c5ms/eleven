package com.motiveschina.erp.inventory;

import com.motiveschina.erp.inventory.command.InventoryStockInCommand;
import com.motiveschina.erp.inventory.event.InventoryStockInEvent;
import com.motiveschina.erp.purchase.event.PurchaseOrderCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryListener {

    private final InventoryService inventoryService;

    @EventListener(PurchaseOrderCompletedEvent.class)
    public void on(PurchaseOrderCompletedEvent event) {
        var orderId = event.getOrder().getOrderId();
        var command = InventoryStockInCommand.builder()
            .purchaseOrderId(orderId)
            .build();
        inventoryService.stockId(command);
    }


    @EventListener(InventoryStockInEvent.class)
    public void on(InventoryStockInEvent event) {
        log.info("Inventory has stocked in : {}", event.getInventory().getProductId());
    }
}
