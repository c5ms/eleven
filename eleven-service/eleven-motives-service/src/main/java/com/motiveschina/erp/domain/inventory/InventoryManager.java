package com.motiveschina.erp.domain.inventory;

import com.motiveschina.core.concurrency.DistributedLock;
import com.motiveschina.erp.domain.inventory.event.InventoryLowStockEvent;
import com.motiveschina.erp.domain.inventory.event.InventoryStockInEvent;
import com.motiveschina.core.DomainSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryManager {

	private final InventoryRepository inventoryRepository;
	private final DistributedLock distributedLock;

	public void stockIn(StockInManifest manifest) {
		var inventory = getInventoryOf(manifest);


		try {
			distributedLock.lock(inventory);

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

		}finally {
			distributedLock.unlock(inventory);
		}


	}

	private Inventory getInventoryOf(StockInManifest manifest) {
		return inventoryRepository.findByProductId(manifest.getProduceId())
				.orElseGet(() -> Inventory.of(manifest.getProduceId(), 0));
	}
}
