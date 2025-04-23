package com.motiveschina.erp.application.command;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public final class PurchaseOrderFilter {
    private String status;
}
