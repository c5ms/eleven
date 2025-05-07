package com.motiveschina.erp.endpoint.resource;

import com.eleven.framework.web.annonation.AsRestApi;
import com.eleven.framework.web.model.PageResult;
import com.eleven.framework.web.model.Pagination;
import com.motiveschina.erp.application.convertor.InventoryConvertor;
import com.motiveschina.erp.application.model.InventoryDto;
import com.motiveschina.erp.domain.inventory.InventoryFilter;
import com.motiveschina.erp.domain.inventory.InventoryFinder;
import com.motiveschina.erp.endpoint.model.InventoryQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Tag(name = "inventory")
@AsRestApi
@RequiredArgsConstructor
@RequestMapping("/inventories")
public class InventoryResource {

    private final InventoryFinder inventoryFinder;
    private final InventoryConvertor inventoryConvertor;

    @Operation(summary = "query purchase order")
    @GetMapping
    public PageResult<InventoryDto> listInventories(@ParameterObject @Validated InventoryQuery query,
                                                    @ParameterObject @Validated Pagination pagination) {
        var filter = InventoryFilter.builder()
            .materialId(query.getMaterialId())
            .build();
        var result = inventoryFinder.query(filter, pagination.toPageable()).map(inventoryConvertor::toDto);
        return PageResult.of(result.getContent(), result.getTotalElements());
    }


}
