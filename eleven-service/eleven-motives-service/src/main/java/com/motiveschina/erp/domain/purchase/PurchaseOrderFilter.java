package com.motiveschina.erp.domain.purchase;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public final class PurchaseOrderFilter {
    private String status;
}
