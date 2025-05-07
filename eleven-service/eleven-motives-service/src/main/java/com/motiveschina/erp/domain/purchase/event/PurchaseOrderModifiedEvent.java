package com.motiveschina.erp.domain.purchase.event;

import com.motiveschina.core.layer.DomainEvent;
import com.motiveschina.erp.domain.purchase.PurchaseOrder;
import com.motiveschina.erp.domain.purchase.PurchaseOrderPatch;
import lombok.AllArgsConstructor;
import lombok.Value;


@Value
@AllArgsConstructor(staticName = "of")
public class PurchaseOrderModifiedEvent implements DomainEvent {
    PurchaseOrder order;
    PurchaseOrderPatch patch;
}
