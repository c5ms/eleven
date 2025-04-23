package com.motiveschina.erp.inventory;

import com.motiveschina.erp.inventory.event.InventoryStockInEvent;
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

            var inventory = inventoryRepository.findByProductId(item.produceId()).orElseGet(() -> Inventory.of(item));
            inventory.stockIn(item.quantity());
            inventoryRepository.save(inventory);

            var event= InventoryStockInEvent.of(inventory, item.quantity());
            DomainSupport.publishDomainEvent(event);
        }
    }
}
