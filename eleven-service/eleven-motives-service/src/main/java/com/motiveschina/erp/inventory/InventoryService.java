package com.motiveschina.erp.inventory;

import com.motiveschina.erp.inventory.command.InventoryStockInCommand;
import com.motiveschina.erp.purchase.PurchaseOrderItem;
import com.motiveschina.erp.purchase.PurchaseOrderRepository;
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

    public void stockId(InventoryStockInCommand command) {
        var purchaseOrder = purchaseOrderRepository.findById(command.getPurchaseOrderId()).orElseThrow(DomainSupport::noPrincipalException);

        var manifest = StockInManifest.empty();
        for (PurchaseOrderItem item : purchaseOrder.getItems()) {
            manifest.add(item.getProductId(), item.getQuantity());
        }

        inventoryManager.stockIn(manifest);
    }
}
