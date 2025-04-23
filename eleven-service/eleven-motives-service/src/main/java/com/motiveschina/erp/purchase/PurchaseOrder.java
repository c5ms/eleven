package com.motiveschina.erp.purchase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.eleven.framework.utils.ImmutableValues;
import com.motiveschina.erp.support.DomainSupport;
import com.motiveschina.erp.support.layer.DomainEntity;
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

    public void addItem(PurchaseOrderItem item) {
        item.setPurchaseOrder(this);
        this.items.add(item);
    }

    public ImmutableValues<PurchaseOrderItem> getItems() {
        return ImmutableValues.of(items);
    }

    public void submit() {
        this.setStatus(STATUS_SUBMITTED);
    }

    public void approve() {
        DomainSupport.must(StringUtils.equals(STATUS_SUBMITTED, this.getStatus()), PurchaseErrors.ORDER_NOT_SUBMITTED);

        this.setStatus(STATUS_APPROVED);
    }

    public void reject() {
        DomainSupport.must(StringUtils.equals(STATUS_SUBMITTED, this.getStatus()), PurchaseErrors.ORDER_NOT_SUBMITTED);

        this.setStatus(STATUS_REJECTED);
    }
}
