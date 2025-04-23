package com.motiveschina.erp.application;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public final class PurchaseOrderFilter {
	private String status;
}
