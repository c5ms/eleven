package com.motiveschina.erp.domain.inventory;

import com.motiveschina.core.domain.DomainHelper;
import com.motiveschina.core.layer.DomainManager;
import com.motiveschina.erp.domain.inventory.event.InventoryLowStockEvent;
import com.motiveschina.erp.domain.inventory.event.InventoryStockInEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryManager implements DomainManager {

    private final InventoryCoordinator inventoryCoordinator;
    private final InventoryRepository inventoryRepository;
    private final InventoryTransactionRepository inventoryTransactionRepository;

    public InventoryTracing stockIn(InventoryTransaction transaction) {
        try {
            inventoryCoordinator.begin(transaction);
            return doStockIn(transaction);
        } finally {
            inventoryCoordinator.finish(transaction);
        }
    }

    private InventoryTracing doStockIn(InventoryTransaction transaction) {
        var inventory = getInventoryOf(transaction);
        var quantity = transaction.getQuantity();
        var tracing = new InventoryTracing(inventory);

        inventory.stockIn(transaction);

        //  todo trigger event before saving or after?
        inventoryRepository.save(inventory);
        inventoryTransactionRepository.save(transaction);

        {
            var event = InventoryStockInEvent.of(inventory, quantity);
            DomainHelper.publishDomainEvent(event);
        }

        if (inventory.isLow()) {
            var event = InventoryLowStockEvent.of(inventory, quantity);
            DomainHelper.publishDomainEvent(event);
        }

        tracing.end();
        return tracing;
    }

    private Inventory getInventoryOf(InventoryTransaction transaction) {
        var materialId = transaction.getMaterialId();
        return inventoryRepository.findByMaterialId(materialId)
            .orElseGet(() -> Inventory.of(materialId, 0));
    }
}
