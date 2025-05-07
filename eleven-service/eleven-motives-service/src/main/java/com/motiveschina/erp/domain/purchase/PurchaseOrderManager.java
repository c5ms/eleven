package com.motiveschina.erp.domain.purchase;

import java.util.List;
import com.motiveschina.core.domain.DomainHelper;
import com.motiveschina.core.layer.DomainManager;
import com.motiveschina.erp.domain.purchase.event.PurchaseOrderApprovedEvent;
import com.motiveschina.erp.domain.purchase.event.PurchaseOrderCompletedEvent;
import com.motiveschina.erp.domain.purchase.event.PurchaseOrderCreatedEvent;
import com.motiveschina.erp.domain.purchase.event.PurchaseOrderDeletedEvent;
import com.motiveschina.erp.domain.purchase.event.PurchaseOrderModifiedEvent;
import com.motiveschina.erp.domain.purchase.event.PurchaseOrderRejectedEvent;
import com.motiveschina.erp.domain.purchase.event.PurchaseOrderSubmittedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PurchaseOrderManager implements DomainManager {
    private final List<PurchaseOrderInterceptor> interceptors;
    private final PurchaseOrderRepository purchaseOrderRepository;

    public void createOrder(PurchaseOrder order) {
        interceptors.forEach(purchaseOrderInterceptor -> purchaseOrderInterceptor.preCreate(order));

        purchaseOrderRepository.save(order);

        interceptors.forEach(purchaseOrderInterceptor -> purchaseOrderInterceptor.afterCreate(order));

        var event = PurchaseOrderCreatedEvent.of(order);
        DomainHelper.publishDomainEvent(event);
    }

    public void updateOrder(PurchaseOrder order, PurchaseOrderPatch patch) {
        order.update(patch);
        purchaseOrderRepository.save(order);

        var event = PurchaseOrderModifiedEvent.of(order, patch);
        DomainHelper.publishDomainEvent(event);
    }

    public void deleteOrder(PurchaseOrder order) {
        interceptors.forEach(purchaseOrderInterceptor -> purchaseOrderInterceptor.preDelete(order));

        purchaseOrderRepository.delete(order);

        interceptors.forEach(purchaseOrderInterceptor -> purchaseOrderInterceptor.afterDelete(order));

        var event = PurchaseOrderDeletedEvent.of(order);
        DomainHelper.publishDomainEvent(event);
    }

    public void submit(PurchaseOrder order) {
        interceptors.forEach(purchaseOrderInterceptor -> purchaseOrderInterceptor.preSubmit(order));

        order.submit();
        purchaseOrderRepository.save(order);

        interceptors.forEach(purchaseOrderInterceptor -> purchaseOrderInterceptor.afterSubmit(order));

        var event = PurchaseOrderSubmittedEvent.of(order);
        DomainHelper.publishDomainEvent(event);
    }

    public void approve(PurchaseOrder order) {
        interceptors.forEach(purchaseOrderInterceptor -> purchaseOrderInterceptor.preApprove(order));

        order.approve();
        purchaseOrderRepository.save(order);

        interceptors.forEach(purchaseOrderInterceptor -> purchaseOrderInterceptor.afterApprove(order));

        var event = PurchaseOrderApprovedEvent.of(order);
        DomainHelper.publishDomainEvent(event);
    }

    public void reject(PurchaseOrder order) {
        interceptors.forEach(purchaseOrderInterceptor -> purchaseOrderInterceptor.preReject(order));

        order.reject();
        purchaseOrderRepository.save(order);

        interceptors.forEach(purchaseOrderInterceptor -> purchaseOrderInterceptor.afterReject(order));

        var event = PurchaseOrderRejectedEvent.of(order);
        DomainHelper.publishDomainEvent(event);
    }

    public void complete(PurchaseOrder order) {
        interceptors.forEach(purchaseOrderInterceptor -> purchaseOrderInterceptor.preComplete(order));

        order.complete();
        purchaseOrderRepository.save(order);

        interceptors.forEach(purchaseOrderInterceptor -> purchaseOrderInterceptor.afterComplete(order));

        var event = PurchaseOrderCompletedEvent.of(order);
        DomainHelper.publishDomainEvent(event);
    }
}
