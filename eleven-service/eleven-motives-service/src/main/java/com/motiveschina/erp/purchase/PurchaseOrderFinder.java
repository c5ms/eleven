package com.motiveschina.erp.purchase;

import java.util.Objects;
import java.util.Optional;
import com.eleven.framework.domain.Specifications;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PurchaseOrderFinder {

    private final PurchaseOrderRepository purchaseOrderRepository;

    public Optional<PurchaseOrder> get(Long orderId) {
        return purchaseOrderRepository.findById(orderId);
    }

    public Page<PurchaseOrder> queryPage(PurchaseOrderFilter filter, Pageable pageable) {
        var spec = Specifications.query(PurchaseOrder.class)
            .and(Objects.nonNull(filter.getId()), Specs.idIs(filter.getId()))
            .getSpec();
        return purchaseOrderRepository.findAll(spec, pageable);
    }

    @UtilityClass
    public class Specs {

        Specification<PurchaseOrder> idIs(@Nullable Long id) {
            return (root, query, builder) ->
                builder.equal(root.get(PurchaseOrder.Fields.orderId), id);
        }

    }

}
