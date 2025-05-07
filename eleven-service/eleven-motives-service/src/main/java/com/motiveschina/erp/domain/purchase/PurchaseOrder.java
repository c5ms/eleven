package com.motiveschina.erp.domain.purchase;

import com.eleven.framework.domain.DomainError;
import com.eleven.framework.domain.DomainValidator;
import com.eleven.framework.domain.SimpleDomainError;
import com.eleven.framework.utils.ImmutableValues;
import com.motiveschina.core.domain.Audition;
import com.motiveschina.core.layer.support.AbstractDomainEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Table(name = "purchase_order")
@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@FieldNameConstants
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseOrder extends AbstractDomainEntity {

    public final static DomainError ERR_ORDER_NOT_INITIALIZED = SimpleDomainError.of("order_not_initialized", "the Purchase order is not just initialized");
    public final static DomainError ERR_ORDER_NOT_SUBMITTED = SimpleDomainError.of("order_not_submitted", "the Purchase order has not been submitted");
    public final static DomainError ERR_ORDER_NOT_APPROVED = SimpleDomainError.of("order_not_approved", "the Purchase order has not been approved");

    public final static String STATUS_INITIALIZED = "initialized";
    public final static String STATUS_SUBMITTED = "submitted";
    public final static String STATUS_APPROVED = "approved";
    public final static String STATUS_REJECTED = "rejected";
    public final static String STATUS_COMPLETED = "completed";

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(name = "order_number", unique = true, nullable = false)
    private String orderNumber;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate = LocalDate.now();

    @Column(name = "supplier_id", nullable = false)
    private Long supplierId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "amount", nullable = false)
    private double amount = 0;

    @Embedded
    private Audition audition = Audition.empty();

    @JoinColumn(name = "purchase_order_id", nullable = false)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PurchaseOrderItem> items = new ArrayList<>();

    @Builder
    @SuppressWarnings("unused")
    private PurchaseOrder(String orderNumber,
                          Long supplierId,
                          Collection<PurchaseOrderItem> items) {
        this.setSupplierId(supplierId);
        this.setOrderNumber(orderNumber);
        this.setStatus(STATUS_INITIALIZED);
        this.setOrderDate(LocalDate.now());
        this.setItems(new ArrayList<>(items));
    }


    public boolean isState(String state) {
        return StringUtils.equals(state, this.getStatus());
    }

    public ImmutableValues<PurchaseOrderItem> getItems() {
        return ImmutableValues.of(items);
    }

    private void calculateAmount() {
        this.amount = 0D;
        for (PurchaseOrderItem item : this.getItems()) {
            this.amount += item.getAmount();
        }
    }

    void setItems(List<PurchaseOrderItem> items) {
        this.items.clear();
        this.items.addAll(items);
        this.calculateAmount();
    }

    void update(PurchaseOrderPatch patch) {
        if (Objects.nonNull(patch.getItems())) {
            this.setItems(patch.getItems());
        }

        if (Objects.nonNull(patch.getSupplierId())) {
            this.setSupplierId(patch.getSupplierId());
        }

    }

    void submit() {
        DomainValidator.must(this.isState(STATUS_INITIALIZED), ERR_ORDER_NOT_INITIALIZED);

        this.setStatus(STATUS_SUBMITTED);
    }

    void approve() {
        DomainValidator.must(this.isState(STATUS_SUBMITTED), ERR_ORDER_NOT_SUBMITTED);

        this.setStatus(STATUS_APPROVED);
    }

    void reject() {
        DomainValidator.must(this.isState(STATUS_SUBMITTED), ERR_ORDER_NOT_SUBMITTED);

        this.setStatus(STATUS_REJECTED);
    }

    void complete() {
//        DomainValidator.must(this.isState(STATUS_APPROVED), ERR_ORDER_NOT_APPROVED);
        this.setStatus(STATUS_COMPLETED);
    }
}
