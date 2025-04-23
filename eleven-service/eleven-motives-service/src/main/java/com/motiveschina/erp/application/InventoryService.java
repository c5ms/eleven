package com.motiveschina.erp.application;

import com.motiveschina.erp.application.command.InventoryStockInCommand;
import com.motiveschina.erp.domain.inventory.Inventory;
import com.motiveschina.erp.domain.inventory.InventoryManager;
import com.motiveschina.erp.domain.inventory.InventoryRepository;
import com.motiveschina.erp.domain.inventory.StockInManifest;
import com.motiveschina.erp.domain.purchase.PurchaseOrderItem;
import com.motiveschina.erp.domain.purchase.PurchaseOrderRepository;
import com.motiveschina.erp.support.DomainSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryManager inventoryManager;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final InventoryRepository inventoryRepository;

    public void check() {
        var lows = inventoryRepository.findLowInventories();
        for (Inventory low : lows) {
            // notify
        }
    }

    public void stockIn(InventoryStockInCommand command) {
        var purchaseOrder = purchaseOrderRepository.findById(command.getPurchaseOrderId()).orElseThrow(DomainSupport::noPrincipalException);

        var manifest = StockInManifest.empty();
        for (PurchaseOrderItem item : purchaseOrder.getItems()) {
            manifest.add(item.getProductId(), item.getQuantity());
        }

        inventoryManager.stockIn(manifest);
    }


}
