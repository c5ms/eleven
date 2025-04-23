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
		var inventory = getInventoryOf(manifest);
		inventory.stockIn(manifest.getQuantity());
		inventoryRepository.saveAndFlush(inventory);

		{
			var event = InventoryStockInEvent.of(inventory, manifest.getQuantity());
			DomainSupport.publishDomainEvent(event);
		}

		if (inventory.isLow()) {
			var event = InventoryLowStockEvent.of(inventory, manifest.getQuantity());
			DomainSupport.publishDomainEvent(event);
		}
	}

	private Inventory getInventoryOf(StockInManifest manifest) {
		return inventoryRepository.findByProductId(manifest.getProduceId())
				.orElseGet(() -> Inventory.of(manifest.getProduceId(), 0));
	}
}
