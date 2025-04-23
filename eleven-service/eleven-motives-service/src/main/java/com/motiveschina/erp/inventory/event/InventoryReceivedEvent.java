package com.motiveschina.erp.inventory.event;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

/**
 * 库存入库事件。当接收到采购订单审核通过事件，或者其他途径导致库存数量增加时触发此事件。
 * 携带商品的基本信息、入库数量、入库时间以及关联的采购订单 ID（如果是采购入库）。
 */
@Value
@Builder
public class InventoryReceivedEvent {
    /**
     * 商品的唯一标识符
     */
    Long productId;
    /**
     * 入库的商品数量
     */
    int receivedQuantity;
    /**
     * 商品入库的时间
     */
    Date receiptTime;
    /**
     * 关联的采购订单的唯一标识符（如果是采购入库）
     */
    Long purchaseOrderId;
}
