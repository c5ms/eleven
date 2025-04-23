package com.motiveschina.erp.inventory.event;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

/**
 * 库存预警事件。当商品的当前库存数量低于预设的安全库存水平时触发此事件。
 * 携带商品的基本信息、当前库存数量、安全库存数量以及预警时间。
 */
@Value
@Builder
public class InventoryLowStockEvent {
    /**
     * 商品的唯一标识符
     */
    Long productId;
    /**
     * 商品的当前库存数量
     */
    int currentQuantity;
    /**
     * 商品的安全库存数量
     */
    int safetyStock;
    /**
     * 触发库存预警的时间
     */
    Date warningTime;
}
