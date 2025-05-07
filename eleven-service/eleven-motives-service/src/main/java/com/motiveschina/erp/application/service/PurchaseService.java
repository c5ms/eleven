package com.motiveschina.erp.application.service;

import com.eleven.framework.domain.DomainError;
import com.eleven.framework.domain.IdentityGenerator;
import com.eleven.framework.domain.SimpleDomainError;
import com.eleven.framework.domain.support.RaindropGenerator;
import com.motiveschina.core.layer.ApplicationService;
import com.motiveschina.erp.application.command.PurchaseOrderCompleteCommand;
import com.motiveschina.erp.application.command.PurchaseOrderCreateCommand;
import com.motiveschina.erp.application.command.PurchaseOrderDeleteCommand;
import com.motiveschina.erp.application.command.PurchaseOrderModifyCommand;
import com.motiveschina.erp.application.command.PurchaseOrderReviewCommand;
import com.motiveschina.erp.application.command.PurchaseOrderSubmitCommand;
import com.motiveschina.erp.application.model.PurchaseOrderDto;
import com.motiveschina.erp.domain.finance.FinanceManager;
import com.motiveschina.erp.domain.finance.PurchaseCost;
import com.motiveschina.erp.domain.inventory.InventoryManager;
import com.motiveschina.erp.domain.inventory.InventoryTransaction;
import com.motiveschina.erp.domain.material.MaterialFinder;
import com.motiveschina.erp.domain.purchase.PurchaseOrder;
import com.motiveschina.erp.domain.purchase.PurchaseOrderFinder;
import com.motiveschina.erp.domain.purchase.PurchaseOrderItem;
import com.motiveschina.erp.domain.purchase.PurchaseOrderManager;
import com.motiveschina.erp.domain.purchase.PurchaseOrderMaterial;
import com.motiveschina.erp.domain.purchase.PurchaseOrderPatch;
import io.micrometer.tracing.SpanName;
import io.micrometer.tracing.Tracer;
import jdk.jfr.StackTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@PreAuthorize("isAnonymous()")
@RequiredArgsConstructor
public class PurchaseService implements ApplicationService {

    public static DomainError ERROR_NO_SUCH_MATERIAL = SimpleDomainError.of("no_such_material", "the materials don't exist");

    private final IdentityGenerator orderNumberGenerator = new RaindropGenerator();

    private final FinanceManager financeManager;
    private final InventoryManager inventoryManager;
    private final PurchaseOrderManager purchaseOrderManager;

    private final MaterialFinder materialFinder;
    private final PurchaseOrderFinder purchaseOrderFinder;


    @SpanName("createPurchaseOrder")
    public PurchaseOrder createPurchaseOrder(PurchaseOrderCreateCommand command) {
        // 1. create the entire order
        var order = PurchaseOrder.builder()
            .orderNumber(nextOrderNumber())
            .items(command.getItems().toList(this::createPurchaseOrderItem))
            .supplierId(command.getSupplierId())
            .build();

        // 2. invoke the order creation logic
        purchaseOrderManager.createOrder(order);

        return order;
    }

    public void updatePurchaseOrder(PurchaseOrderModifyCommand command) {
        var order = purchaseOrderFinder.require(command.getOrderId());

        var patch = PurchaseOrderPatch.builder()
            .items(command.getItems().toList(this::createPurchaseOrderItem))
            .supplierId(command.getSupplierId())
            .build();

        purchaseOrderManager.updateOrder(order, patch);
    }

    public void submitPurchaseOrder(PurchaseOrderSubmitCommand command) {
        var order = purchaseOrderFinder.require(command.getOrderId());
        purchaseOrderManager.submit(order);
    }

    public void reviewPurchaseOrder(PurchaseOrderReviewCommand command) {
        var order = purchaseOrderFinder.require(command.getOrderId());

        if (command.getPass()) {
            purchaseOrderManager.approve(order);
        } else {
            purchaseOrderManager.reject(order);
        }
    }

    public void deletePurchaseOrder(PurchaseOrderDeleteCommand command) {
        var order = purchaseOrderFinder.require(command.getOrderId());
        purchaseOrderManager.deleteOrder(order);
    }

    public void completePurchaseOrder(PurchaseOrderCompleteCommand command) {
        var order = purchaseOrderFinder.require(command.getOrderId());

        //  complete by the domain logic
        purchaseOrderManager.complete(order);

        //  stock in for each item in the order
        var tracings = order.getItems()
            .stream()
            .map(item -> createTransaction(order, item))
            .map(inventoryManager::stockIn)
            .toList();

        // record the purchase cost
        var cost = PurchaseCost.builder()
            .purchaseOrderId(order.getOrderId())
            .purchaseCost(order.getAmount())
            .transportationCost(command.getTransportationCost())
            .build();

        financeManager.createCost(cost);
    }

    private String nextOrderNumber() {
        return String.format("PD%s", orderNumberGenerator.next());
    }

    private InventoryTransaction createTransaction(PurchaseOrder order, PurchaseOrderItem item) {
        return InventoryTransaction.fromPurchase()
            .materialId(item.getMaterial().getMaterialId())
            .quantity(item.getQuantity())
            .purchaseOrderId(order.getOrderId())
            .build();
    }

    // todo do we need to extract this method into converter?
    private PurchaseOrderItem createPurchaseOrderItem(PurchaseOrderDto.Item dto) {
        var material = materialFinder.get(dto.getMaterialId()).orElseThrow(ERROR_NO_SUCH_MATERIAL::toException);
        return PurchaseOrderItem.builder()
            .material(PurchaseOrderMaterial.of(material.getMaterialId(),material.getMaterialCode()))
            .price(dto.getPrice())
            .quantity(dto.getQuantity())
            .build();
    }

}
