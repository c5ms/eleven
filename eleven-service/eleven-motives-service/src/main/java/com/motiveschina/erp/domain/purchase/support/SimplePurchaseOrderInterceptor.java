package com.motiveschina.erp.domain.purchase.support;

import com.motiveschina.erp.domain.purchase.PurchaseOrder;
import com.motiveschina.erp.domain.purchase.PurchaseOrderInterceptor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class SimplePurchaseOrderInterceptor implements PurchaseOrderInterceptor {

    @Override
    public void preCreate(PurchaseOrder order) {

    }

}
