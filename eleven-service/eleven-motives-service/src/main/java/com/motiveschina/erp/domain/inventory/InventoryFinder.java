package com.motiveschina.erp.domain.inventory;

import java.util.List;
import java.util.Objects;
import com.eleven.framework.domain.Specifications;
import com.motiveschina.core.layer.DomainFinder;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryFinder implements DomainFinder {

    private final InventoryRepository inventoryRepository;

    public List<Inventory> query(InventoryFilter filter) {
        var spec = createSpec(filter);
        return inventoryRepository.findAll(spec);
    }

    public Page<Inventory> query(InventoryFilter filter, Pageable page) {
        var spec = createSpec(filter);
        return inventoryRepository.findAll(spec, page);
    }

    private Specification<Inventory> createSpec(InventoryFilter filter) {
        return Specifications.query(Inventory.class)
            .and(Objects.nonNull(filter.getMaterialId()), Specs.materialIdIs(filter.getMaterialId()))
            .and(filter.isLow(), Specs.isLow())
            .getSpec();
    }

    @UtilityClass
    public class Specs {

        Specification<Inventory> materialIdIs(@Nullable Long id) {
            return (root, query, builder) ->
                builder.equal(root.get(Inventory.Fields.materialId), id);
        }

        Specification<Inventory> isLow() {
            return (root, query, builder) ->
                builder.isTrue(root.get(Inventory.Fields.isLow));
        }

    }

}
