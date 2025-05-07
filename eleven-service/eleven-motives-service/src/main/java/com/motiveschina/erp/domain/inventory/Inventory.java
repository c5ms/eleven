package com.motiveschina.erp.domain.inventory;

import java.time.LocalDate;
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
@Setter(AccessLevel.PRIVATE)
@FieldNameConstants
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inventory implements DomainEntity {
    public static int SCHEMA_VERSION_V1 = 1;

    @Id
    @Column(name = "inventory_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    @Column(name = "material_id", unique = true, nullable = false)
    private Long materialId;

    @Column(name = "current_quantity", nullable = false)
    private int currentQuantity;

    @Column(name = "safety_stock", nullable = false)
    private int safetyStock;

    @Column(name = "is_low", nullable = false)
    private boolean isLow;

    @Column(name = "last_operate_date")
    private LocalDate lastOperateDate;

    @Version
    @Column(name = "operate_version", nullable = false)
    private long operateVersion = 0;

    @Column(name = "schema_version", nullable = false)
    private long schemaVersion = SCHEMA_VERSION_V1;

    public static Inventory of(Long materialId, int safetyStock) {
        var inventory = new Inventory();
        inventory.setMaterialId(materialId);
        inventory.setCurrentQuantity(0);
        inventory.setSafetyStock(safetyStock);
        return inventory;
    }

    public void stockIn(InventoryTransaction transaction) {
        var finalQuantity = currentQuantity + transaction.getQuantity();
        this.setCurrentQuantity(finalQuantity);
        this.setLastOperateDate(transaction.getOperateDate());
    }

    private void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
        tidy();
    }

    private void setSafetyStock(int safetyStock) {
        this.safetyStock = safetyStock;
        tidy();
    }

    private void tidy() {
        this.isLow = this.currentQuantity - this.safetyStock <= 0;
    }
}
