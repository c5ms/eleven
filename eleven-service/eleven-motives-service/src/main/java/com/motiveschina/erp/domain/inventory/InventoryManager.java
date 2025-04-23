package com.motiveschina.erp.domain.inventory;

import com.motiveschina.core.domain.DomainSupport;
import com.motiveschina.erp.domain.inventory.event.InventoryLowStockEvent;
import com.motiveschina.erp.domain.inventory.event.InventoryStockInEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryManager {

    private final InventoryRepository inventoryRepository;
    private final TransactionRepository transactionRepository;

    public void stockIn(Transaction transaction) {
        transactionRepository.save(transaction);

        var produceId = transaction.getProductId();
        var inventory = getInventoryOf(produceId);
        var quantity = transaction.getQuantity();

        inventory.stockIn(quantity);
        inventoryRepository.saveAndFlush(inventory);

        {
            var event = InventoryStockInEvent.of(inventory, quantity);
            DomainSupport.publishDomainEvent(event);
        }

        if (inventory.isLow()) {
            var event = InventoryLowStockEvent.of(inventory, quantity);
            DomainSupport.publishDomainEvent(event);
        }
    }

    private Inventory getInventoryOf(Long productId) {
        return inventoryRepository.findByProductId(productId)
            .orElseGet(() -> Inventory.of(productId, 0));
    }
}
