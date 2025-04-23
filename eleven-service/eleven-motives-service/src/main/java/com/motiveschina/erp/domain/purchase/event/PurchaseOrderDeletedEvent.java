package com.motiveschina.erp.domain.purchase.event;

import com.motiveschina.erp.domain.purchase.PurchaseOrder;
import com.motiveschina.core.layer.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * 采购订单删除事件。当处于草稿状态的采购订单被删除时触发此事件。
 * 携带采购订单的基本信息以及删除时间。
 */
@Value
@AllArgsConstructor(staticName = "of")
public class PurchaseOrderDeletedEvent implements DomainEvent {
    PurchaseOrder order;
}
