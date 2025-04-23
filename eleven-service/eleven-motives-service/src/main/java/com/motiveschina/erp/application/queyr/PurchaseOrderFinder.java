package com.motiveschina.erp.application.queyr;

import java.util.Optional;
import com.eleven.framework.domain.Specifications;
import com.motiveschina.erp.domain.purchase.PurchaseOrder;
import com.motiveschina.erp.domain.purchase.PurchaseOrderRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
            .and(StringUtils.isNotBlank(filter.getStatus()), Specs.statusIs(filter.getStatus()))
            .getSpec();
        return purchaseOrderRepository.findAll(spec, pageable);
    }

    @UtilityClass
    public class Specs {

        Specification<PurchaseOrder> statusIs(@Nullable String status) {
            return (root, query, builder) ->
                builder.equal(root.get(PurchaseOrder.Fields.status), status);
        }

    }

}
