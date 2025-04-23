package com.motiveschina.erp.domain.purchase;

import com.eleven.framework.utils.ImmutableValues;
import com.motiveschina.core.domain.DomainSupport;
import com.motiveschina.core.layer.DomainEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "purchase_order")
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@FieldNameConstants
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseOrder implements DomainEntity {

    private final static String STATUS_INITIALIZED = "initialized";
    private final static String STATUS_SUBMITTED = "submitted";
    private final static String STATUS_APPROVED = "approved";
    private final static String STATUS_REJECTED = "rejected";
    private final static String STATUS_COMPLETED = "completed";

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(name = "order_number", unique = true, nullable = false)
    private String orderNumber;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "supplier_id", nullable = false)
    private Long supplierId;

    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PurchaseOrderItem> items = new ArrayList<>();

    @SuppressWarnings("unused")
    @Builder
    private static PurchaseOrder of(String orderNumber,
                                   Long supplierId,
                                   Collection<PurchaseOrderItem> items) {
        var order = new PurchaseOrder();
        order.setSupplierId(supplierId);
        order.setOrderNumber(orderNumber);
        order.setStatus(STATUS_INITIALIZED);
        order.setOrderDate(LocalDate.now());
        items.forEach(order::addItem);
        return order;
    }

    public boolean isState(String state) {
        return StringUtils.equals(state, this.getStatus());
    }

    public ImmutableValues<PurchaseOrderItem> getItems() {
        return ImmutableValues.of(items);
    }

    public double getTotalPrice() {
        double price = 0D;
        for (PurchaseOrderItem item : this.items) {
            price += item.getPrice();
        }
        return price;
    }

    void addItem(PurchaseOrderItem item) {
        item.setPurchaseOrder(this);
        this.items.add(item);
    }

    void submit() {
        this.setStatus(STATUS_SUBMITTED);
    }

    void approve() {
        DomainSupport.must(this.isState(STATUS_SUBMITTED), PurchaseErrors.ORDER_NOT_SUBMITTED);

        this.setStatus(STATUS_APPROVED);
    }

    void reject() {
        DomainSupport.must(this.isState(STATUS_SUBMITTED), PurchaseErrors.ORDER_NOT_SUBMITTED);

        this.setStatus(STATUS_REJECTED);
    }

    void complete() {
        DomainSupport.must(this.isState(STATUS_APPROVED), PurchaseErrors.ORDER_NOT_APPROVED);
        this.setStatus(STATUS_COMPLETED);
    }
}
