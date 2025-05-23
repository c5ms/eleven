package com.motiveschina.erp.domain.purchase.event;

import com.motiveschina.core.layer.DomainEvent;
import com.motiveschina.erp.domain.purchase.PurchaseOrder;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class PurchaseOrderCreatedEvent implements DomainEvent {
    PurchaseOrder order;
}
