package com.motiveschina.erp.application;

import com.motiveschina.erp.domain.purchase.event.PurchaseOrderApprovedEvent;
import com.motiveschina.erp.domain.purchase.event.PurchaseOrderCreatedEvent;
import com.motiveschina.erp.domain.purchase.event.PurchaseOrderDeletedEvent;
import com.motiveschina.erp.domain.purchase.event.PurchaseOrderRejectedEvent;
import com.motiveschina.erp.domain.purchase.event.PurchaseOrderSubmittedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PurchaseListener {

    @EventListener(PurchaseOrderCreatedEvent.class)
    public void on(PurchaseOrderCreatedEvent event) {
        log.info("PurchaseOrder has been created : {}", event.getOrder().getOrderId());
    }

    @EventListener(PurchaseOrderSubmittedEvent.class)
    public void on(PurchaseOrderSubmittedEvent event) {
        log.info("PurchaseOrder has been submitted : {}", event.getOrder().getOrderId());
    }

    @EventListener(PurchaseOrderDeletedEvent.class)
    public void on(PurchaseOrderDeletedEvent event) {
        log.info("PurchaseOrder has been deleted : {}", event.getOrder().getOrderId());
    }

    @EventListener(PurchaseOrderApprovedEvent.class)
    public void on(PurchaseOrderApprovedEvent event) {
        log.info("PurchaseOrder has been approved : {}", event.getOrder().getOrderId());
    }

    @EventListener(PurchaseOrderRejectedEvent.class)
    public void on(PurchaseOrderRejectedEvent event) {
        log.info("PurchaseOrder has been rejected : {}", event.getOrder().getOrderId());
    }
}
