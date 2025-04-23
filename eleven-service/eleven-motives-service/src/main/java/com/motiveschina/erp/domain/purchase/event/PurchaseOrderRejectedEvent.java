package com.motiveschina.erp.domain.purchase.event;

import com.motiveschina.erp.domain.purchase.PurchaseOrder;
import com.motiveschina.erp.support.layer.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * 采购订单审核不通过事件。当采购订单审核结果为不通过时触发此事件。
 * 携带采购订单的基本信息、审核不通过时间、审核人员信息以及不通过原因。
 */
@Value
@AllArgsConstructor(staticName = "of")
public class PurchaseOrderRejectedEvent implements DomainEvent {
    PurchaseOrder order;
}

