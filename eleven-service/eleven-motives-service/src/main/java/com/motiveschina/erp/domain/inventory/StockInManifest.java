package com.motiveschina.erp.domain.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class StockInManifest {

	private Long produceId;
	private int quantity;

}
