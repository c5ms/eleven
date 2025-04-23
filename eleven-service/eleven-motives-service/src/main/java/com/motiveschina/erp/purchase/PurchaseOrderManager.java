package com.motiveschina.erp.purchase;

import com.motiveschina.erp.purchase.event.PurchaseOrderApprovedEvent;
import com.motiveschina.erp.purchase.event.PurchaseOrderCreatedEvent;
import com.motiveschina.erp.purchase.event.PurchaseOrderDeletedEvent;
import com.motiveschina.erp.purchase.event.PurchaseOrderRejectedEvent;
import com.motiveschina.erp.purchase.event.PurchaseOrderSubmittedEvent;
import com.motiveschina.erp.support.DomainSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PurchaseOrderManager {

    private final PurchaseOrderRepository purchaseOrderRepository;

    public void createOrder(PurchaseOrder order) {
        purchaseOrderRepository.save(order);

        var event = PurchaseOrderCreatedEvent.of(order);
        DomainSupport.publishDomainEvent(event);
    }

    public void deleteOrder(PurchaseOrder order) {
        purchaseOrderRepository.delete(order);

        var event = PurchaseOrderDeletedEvent.of(order);
        DomainSupport.publishDomainEvent(event);

    }

    public void submit(PurchaseOrder order) {
        order.submit();
        purchaseOrderRepository.save(order);

        var event = PurchaseOrderSubmittedEvent.of(order);
        DomainSupport.publishDomainEvent(event);
    }

    public void approve(PurchaseOrder order) {
        order.approve();
        purchaseOrderRepository.save(order);

        var event = PurchaseOrderApprovedEvent.of(order);
        DomainSupport.publishDomainEvent(event);
    }

    public void reject(PurchaseOrder order) {
        order.reject();
        purchaseOrderRepository.save(order);

        var event = PurchaseOrderRejectedEvent.of(order);
        DomainSupport.publishDomainEvent(event);
    }


}
