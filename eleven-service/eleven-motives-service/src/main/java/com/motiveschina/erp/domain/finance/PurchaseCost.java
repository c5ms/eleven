package com.motiveschina.erp.domain.finance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Table(name = "finance_purchase_cost")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@ToString
@FieldNameConstants
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseCost {

    @Id
    @Column(name = "cost_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long costId;

    @Column(name = "purchase_order_id")
    private Long purchaseOrderId;

    @Column(name = "purchase_cost")
    private double purchaseCost;

    @Column(name = "transportation_cost")
    private double transportationCost;

    @SuppressWarnings("unused")
    @Builder
    private PurchaseCost(Long purchaseOrderId,
                         double purchaseCost,
                         double transportationCost) {
        this.purchaseOrderId = purchaseOrderId;
        this.purchaseCost = purchaseCost;
        this.transportationCost = transportationCost;
    }

}
