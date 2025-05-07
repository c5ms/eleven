package com.motiveschina.erp.domain.inventory;

import java.time.LocalDate;
import java.util.Optional;
import com.motiveschina.core.layer.DomainEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Table(name = "inventory_transaction")
@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@FieldNameConstants
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryTransaction implements DomainEntity {

    public enum Type {
        Purchase
    }

    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(name = "material_id")
    private Long materialId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "purchase_order_id")
    private int purchaseOrderId;

    @Column(name = "operate_date")
    private LocalDate operateDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @SuppressWarnings("unused")
    @Builder(builderClassName = "FromPurchaseBuilder", builderMethodName = "fromPurchase")
    private InventoryTransaction(Long materialId,
                                 Long purchaseOrderId,
                                 LocalDate operateDate,
                                 int quantity) {
        this.setMaterialId(materialId);
        this.setQuantity(quantity);
        this.setType(Type.Purchase);
        this.setOperateDate(Optional.ofNullable(operateDate).orElse(LocalDate.now()));
    }

    public String toInventoryKey() {
        return String.format("%s", materialId);
    }

}
