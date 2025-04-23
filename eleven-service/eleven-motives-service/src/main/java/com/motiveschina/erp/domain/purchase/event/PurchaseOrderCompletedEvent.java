package com.motiveschina.erp.domain.purchase.event;

import com.motiveschina.erp.domain.purchase.PurchaseOrder;
import com.motiveschina.erp.support.layer.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Value;


@Value
@AllArgsConstructor(staticName = "of")
public class PurchaseOrderCompletedEvent implements DomainEvent {
    PurchaseOrder order;
}
