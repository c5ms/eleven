package com.motiveschina.erp.domain.inventory;

import com.motiveschina.core.distributed.Lockable;
import com.motiveschina.core.layer.DomainEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Super;

import java.time.LocalDate;
import java.util.Optional;

@Table(name = "inventory_transaction")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@FieldNameConstants
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transaction implements DomainEntity, Lockable {

    public static enum Type {
        Purchase
    }

    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(name = "product_id")
    private Long productId;

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
    private static Transaction of(Long productId,
                                         Long purchaseOrderId,
                                         LocalDate operateDate,
                                         int quantity) {
        var transaction = new Transaction();
        transaction.setProductId(productId);
        transaction.setQuantity(quantity);
        transaction.setProductId(purchaseOrderId);
        transaction.setType(Type.Purchase);
        transaction.setOperateDate(Optional.ofNullable(operateDate).orElse(LocalDate.now()));
        return transaction;
    }

    @Override
    public String getLockKey() {
        return String.format("%s", productId);
    }
}
