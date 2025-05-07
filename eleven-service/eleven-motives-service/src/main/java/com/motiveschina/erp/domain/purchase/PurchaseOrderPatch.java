package com.motiveschina.erp.domain.purchase;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PurchaseOrderPatch {
    private Long supplierId;
    private List<PurchaseOrderItem> items;
}
