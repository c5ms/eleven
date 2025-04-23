package com.motiveschina.erp.application;

import com.motiveschina.core.domain.DomainSupport;
import com.motiveschina.erp.application.command.*;
import com.motiveschina.erp.application.convertor.PurchaseConvertor;
import com.motiveschina.erp.domain.inventory.InventoryManager;
import com.motiveschina.erp.domain.inventory.Transaction;
import com.motiveschina.erp.domain.purchase.PurchaseOrder;
import com.motiveschina.erp.domain.purchase.PurchaseOrderItem;
import com.motiveschina.erp.domain.purchase.PurchaseOrderManager;
import com.motiveschina.erp.domain.purchase.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@PreAuthorize("isAnonymous()")
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseOrderManager purchaseOrderManager;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseConvertor purchaseConvertor;
    private final InventoryManager inventoryManager;

    public PurchaseOrder createPurchaseOrder(PurchaseOrderCreateCommand command) {
        var number = UUID.randomUUID().toString().toUpperCase();
        var items = purchaseConvertor.toDomain(command.getItems());
        var order = PurchaseOrder.builder()
            .orderNumber(number)
            .items(items)
            .supplierId(command.getSupplierId())
            .build();
        purchaseOrderManager.createOrder(order, items);
        return order;
    }


    public void submitPurchaseOrder(PurchaseOrderSubmitCommand command) {
        var order = purchaseOrderRepository.findById(command.getOrderId()).orElseThrow(DomainSupport::noPrincipalException);
        purchaseOrderManager.submit(order);
    }

    public void reviewPurchaseOrder(PurchaseOrderReviewCommand command) {
        var order = purchaseOrderRepository.findById(command.getOrderId()).orElseThrow(DomainSupport::noPrincipalException);

        if (command.getPass()) {
            purchaseOrderManager.approve(order);
        } else {
            purchaseOrderManager.reject(order);
        }
    }

    public void deletePurchaseOrder(PurchaseOrderDeleteCommand command) {
        var order = purchaseOrderRepository.findById(command.getOrderId()).orElseThrow(DomainSupport::noPrincipalException);
        purchaseOrderManager.deleteOrder(order);
    }

    public void completePurchaseOrder(PurchaseOrderCompleteCommand command) {
        var order = purchaseOrderRepository.findById(command.getOrderId()).orElseThrow(DomainSupport::noPrincipalException);

        // 1. complete by the domain logic
        purchaseOrderManager.complete(order);

        // 2. stock in for each item in the order
        for (PurchaseOrderItem item : order.getItems()) {
            var transaction = Transaction.fromPurchase()
                .productId(item.getProductId())
                .quantity(item.getQuantity())
                .purchaseOrderId(item.getPurchaseOrder().getOrderId())
                .build();
            inventoryManager.stockIn(transaction);
        }
    }
}
