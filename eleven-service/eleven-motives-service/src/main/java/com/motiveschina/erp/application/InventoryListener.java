package com.motiveschina.erp.application;

import com.motiveschina.erp.domain.inventory.event.InventoryLowStockEvent;
import com.motiveschina.erp.domain.inventory.event.InventoryStockInEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryListener {

    @EventListener(InventoryStockInEvent.class)
    public void on(InventoryStockInEvent event) {
        log.info("Inventory has stocked in : {}", event.getInventory().getProductId());
    }

    @EventListener(InventoryLowStockEvent.class)
    public void on(InventoryLowStockEvent event) {
        log.info("Inventory is lacking : {}", event.getInventory().getProductId());
    }


}
