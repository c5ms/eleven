package com.motiveschina.erp.domain.purchase.utils;

import com.motiveschina.erp.domain.purchase.PurchaseOrder;
import com.motiveschina.erp.domain.purchase.PurchaseOrderItem;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PurchaseOrderTestUtil {

    public PurchaseOrder emptyPurchaseOrder() {
        return PurchaseOrder.builder().build();
    }

    public PurchaseOrderItem createItem(int quantity, double price) {
        return PurchaseOrderItem.builder()
            .quantity(quantity)
            .price(price)
            .build();
    }
}
