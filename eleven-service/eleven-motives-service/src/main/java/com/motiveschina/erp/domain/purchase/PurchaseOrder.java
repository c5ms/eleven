package com.motiveschina.erp.domain.purchase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.eleven.framework.utils.ImmutableValues;
import com.motiveschina.core.DomainSupport;
import com.motiveschina.core.layer.DomainEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.StringUtils;

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

    public static PurchaseOrder of(String orderNumber, Long supplierId) {
        var order = new PurchaseOrder();
        order.setSupplierId(supplierId);
        order.setOrderNumber(orderNumber);
        order.setStatus(STATUS_INITIALIZED);
        order.setOrderDate(LocalDate.now());
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
