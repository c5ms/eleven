package com.motiveschina.erp.application.queyr;

import java.util.Objects;
import com.eleven.framework.domain.Specifications;
import com.motiveschina.erp.domain.inventory.Inventory;
import com.motiveschina.erp.domain.inventory.InventoryRepository;
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
public class InventoryFinder {

    private final InventoryRepository inventoryRepository;

    public Page<Inventory> queryPage(InventoryFilter filter, Pageable page) {
        var spec = Specifications.query(Inventory.class)
            .and(Objects.nonNull(filter.getProductIdIs()), Specs.produceIdIs(filter.getProductIdIs()))
            .getSpec();
        return inventoryRepository.findAll(spec, page);
    }

    @UtilityClass
    public class Specs {

        Specification<Inventory> produceIdIs(@Nullable Long id) {
            return (root, query, builder) ->
                builder.equal(root.get(Inventory.Fields.productId), id);
        }

    }

}
