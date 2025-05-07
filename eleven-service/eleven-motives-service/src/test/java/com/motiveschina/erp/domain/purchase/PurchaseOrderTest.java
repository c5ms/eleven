package com.motiveschina.erp.domain.purchase;

import java.util.Arrays;
import com.motiveschina.erp.domain.purchase.utils.PurchaseOrderTestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PurchaseOrderTest {

    @Test
    void getAmount() {
        var order = PurchaseOrder.builder()
            .items(Arrays.asList(
                PurchaseOrderTestUtil.createItem(10, 5),
                PurchaseOrderTestUtil.createItem(6, 2)
            ))
            .build();
        Assertions.assertEquals(10 * 5 + 6 * 2, order.getAmount());
    }

    @Test
    void status() {
        var order = PurchaseOrderTestUtil.emptyPurchaseOrder();
        Assertions.assertTrue(order.isState(PurchaseOrder.STATUS_INITIALIZED));

        order.submit();
        Assertions.assertTrue(order.isState(PurchaseOrder.STATUS_SUBMITTED));

        order.approve();
        Assertions.assertTrue(order.isState(PurchaseOrder.STATUS_APPROVED));

        order.complete();
        Assertions.assertTrue(order.isState(PurchaseOrder.STATUS_COMPLETED));

        order = PurchaseOrderTestUtil.emptyPurchaseOrder();
        order.submit();
        order.reject();
        Assertions.assertTrue(order.isState(PurchaseOrder.STATUS_REJECTED));
    }
}
