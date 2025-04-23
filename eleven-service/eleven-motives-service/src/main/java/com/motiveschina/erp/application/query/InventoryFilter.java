package com.motiveschina.erp.application.query;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public final class InventoryFilter {
    private Long productIdIs;
}
