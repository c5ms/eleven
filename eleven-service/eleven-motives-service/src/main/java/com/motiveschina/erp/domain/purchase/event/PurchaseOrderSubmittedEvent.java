package com.motiveschina.erp.domain.purchase.event;

import com.motiveschina.erp.domain.purchase.PurchaseOrder;
import com.motiveschina.core.layer.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * 采购订单提交审核事件。当采购订单从草稿状态提交进行审核时触发此事件。
 * 携带采购订单的基本信息以及提交审核的时间和当前审核状态。
 */
@Value
@AllArgsConstructor(staticName = "of")
public class PurchaseOrderSubmittedEvent implements DomainEvent {
    PurchaseOrder order;
}
