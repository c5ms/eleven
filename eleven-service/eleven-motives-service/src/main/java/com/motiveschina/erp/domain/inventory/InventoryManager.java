package com.motiveschina.erp.domain.inventory;

import com.motiveschina.erp.domain.inventory.event.InventoryLowStockEvent;
import com.motiveschina.erp.domain.inventory.event.InventoryStockInEvent;
import com.motiveschina.erp.support.DomainSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryManager {

    private final InventoryRepository inventoryRepository;

    public void stockIn(StockInManifest manifest) {
        for (StockInManifest.Item item : manifest.getItems()) {

            var inventory = getInventoryOf(item);
            inventory.stockIn(item.quantity());
            inventoryRepository.saveAndFlush(inventory);

            {
                var event = InventoryStockInEvent.of(inventory, item.quantity());
                DomainSupport.publishDomainEvent(event);
            }

            if (inventory.isLow()) {
                var event = InventoryLowStockEvent.of(inventory, item.quantity());
                DomainSupport.publishDomainEvent(event);
            }
        }
    }

    private Inventory getInventoryOf(StockInManifest.Item item) {
        return inventoryRepository.findByProductId(item.produceId())
            .orElseGet(() -> Inventory.of(item));
    }
}
