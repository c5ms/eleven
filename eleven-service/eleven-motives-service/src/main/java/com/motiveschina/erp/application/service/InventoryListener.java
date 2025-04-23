package com.motiveschina.erp.application.service;

import java.util.concurrent.TimeUnit;
import com.motiveschina.erp.application.command.InventoryStockInCommand;
import com.motiveschina.erp.domain.inventory.event.InventoryLowStockEvent;
import com.motiveschina.erp.domain.inventory.event.InventoryStockInEvent;
import com.motiveschina.erp.domain.purchase.event.PurchaseOrderCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
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
        inventoryService.stockIn(command);
    }

    @EventListener(InventoryStockInEvent.class)
    public void on(InventoryStockInEvent event) {
        log.info("Inventory has stocked in : {}", event.getInventory().getProductId());
    }

    @EventListener(InventoryLowStockEvent.class)
    public void on(InventoryLowStockEvent event) {
        log.info("Inventory is lacking : {}", event.getInventory().getProductId());
    }

    @Scheduled(fixedRate = 2, timeUnit = TimeUnit.SECONDS)
    public void inventory() {
        System.out.println("scan low inventory");
    }

}
