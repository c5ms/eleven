package com.motiveschina.erp.inventory.event;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

/**
 * 库存出库事件。当发生销售出库、调拨出库等操作，导致库存数量减少时触发此事件。
 * 携带商品的基本信息、出库数量、出库时间以及关联的销售订单 ID 或调拨单 ID 等。
 */
@Value
@Builder
public class InventoryShippedEvent {
    /**
     * 商品的唯一标识符
     */
    Long productId;
    /**
     * 出库的商品数量
     */
    int shippedQuantity;
    /**
     * 商品出库的时间
     */
    Date shipmentTime;
    /**
     * 关联的销售订单或调拨单的唯一标识符
     */
    Long relatedOrderId;
}
