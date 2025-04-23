package com.motiveschina.erp.application;

import com.motiveschina.core.distributed.DistributedLock;
import com.motiveschina.core.domain.DomainSupport;
import com.motiveschina.erp.application.command.InventoryStockInCommand;
import com.motiveschina.erp.domain.inventory.InventoryManager;
import com.motiveschina.erp.domain.inventory.Transaction;
import com.motiveschina.erp.domain.purchase.PurchaseOrderItem;
import com.motiveschina.erp.domain.purchase.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class InventoryService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final InventoryManager inventoryManager;
    private final DistributedLock distributedLock;

    public void stockIn(InventoryStockInCommand command) {
        var order = purchaseOrderRepository.findById(command.getPurchaseOrderId()).orElseThrow(DomainSupport::noPrincipalException);
        for (PurchaseOrderItem item : order.getItems()) {
            var transaction = Transaction.fromPurchase()
                .productId(item.getProductId())
                .quantity(item.getQuantity())
                .purchaseOrderId(item.getPurchaseOrder().getOrderId())
                .build();
            try {
                distributedLock.lock(transaction);
                inventoryManager.stockIn(transaction);
            } finally {
                distributedLock.unlock(transaction);
            }
        }
    }


}
