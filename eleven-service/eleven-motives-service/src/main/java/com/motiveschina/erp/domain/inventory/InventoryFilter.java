package com.motiveschina.erp.domain.inventory;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public final class InventoryFilter {
    private Long materialId;
    private boolean isLow;
}
