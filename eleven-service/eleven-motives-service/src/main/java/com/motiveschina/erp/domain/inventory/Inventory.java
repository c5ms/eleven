package com.motiveschina.erp.domain.inventory;

import com.motiveschina.core.concurrency.Lockable;
import com.motiveschina.core.layer.DomainEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Table(name = "inventory")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@FieldNameConstants
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inventory implements DomainEntity, Lockable {

	@Id
	@Column(name = "inventory_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long inventoryId;

	@Column(name = "product_id", unique = true, nullable = false)
	private Long productId;

	@Column(name = "current_quantity", nullable = false)
	private int currentQuantity;

	@Column(name = "safety_stock", nullable = false)
	private int safetyStock;

	@Column(name = "is_low", nullable = false)
	private boolean isLow;

	@Version
	@Column(name = "version", nullable = false)
	private long version;

	public static Inventory of(Long productId, int safetyStock) {
		var inventory = new Inventory();
		inventory.setProductId(productId);
		inventory.setCurrentQuantity(0);
		inventory.setSafetyStock(safetyStock);
		return inventory;
	}


	public void stockIn(int quantity) {
		var finalQuantity = currentQuantity + quantity;
		this.setCurrentQuantity(finalQuantity);
	}

	public void setCurrentQuantity(int currentQuantity) {
		this.currentQuantity = currentQuantity;
		this.isLow = this.currentQuantity - this.safetyStock <= 0;
	}

	@Override
	public String getLockKey() {
		return String.format("Inventory@%s#%s", this.inventoryId, this.getProductId());
	}
}
