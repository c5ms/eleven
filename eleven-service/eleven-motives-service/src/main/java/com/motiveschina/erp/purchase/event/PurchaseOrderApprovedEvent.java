package com.motiveschina.erp.purchase.event;

import com.motiveschina.erp.purchase.PurchaseOrder;
import com.motiveschina.erp.support.layer.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * 采购订单审核通过事件。当采购订单经过审核，结果为通过时触发此事件。
 * 携带采购订单的基本信息、审核通过时间、审核人员信息以及订单中的商品条目信息。
 */
@Value
@AllArgsConstructor(staticName = "of")
public class PurchaseOrderApprovedEvent implements DomainEvent {
    PurchaseOrder order;
}
