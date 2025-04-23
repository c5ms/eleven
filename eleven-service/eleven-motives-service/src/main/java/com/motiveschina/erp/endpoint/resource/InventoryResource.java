package com.motiveschina.erp.endpoint.resource;

import com.eleven.framework.web.annonation.AsRestApi;
import com.eleven.framework.web.model.PageResult;
import com.eleven.framework.web.model.Pagination;
import com.motiveschina.erp.application.convertor.InventoryConvertor;
import com.motiveschina.erp.application.InventoryFilter;
import com.motiveschina.erp.application.InventoryFinder;
import com.motiveschina.erp.application.InventoryService;
import com.motiveschina.erp.application.dto.InventoryDto;
import com.motiveschina.erp.endpoint.request.InventoryQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Tag(name = "purchase-orders")
@AsRestApi
@RequiredArgsConstructor
@RequestMapping("/inventories")
public class InventoryResource {

    private final InventoryFinder inventoryFinder;
    private final InventoryConvertor inventoryConvertor;
    private final InventoryService inventoryService;

    @Operation(summary = "list all purchase order")
    @GetMapping
    public PageResult<InventoryDto> listInventories(@ParameterObject InventoryQuery query,
                                                    @ParameterObject Pagination pagination) {
        var filter = InventoryFilter.builder()
            .productIdIs(query.getProductId())
            .build();
        var result = inventoryFinder.queryPage(filter, pagination.toPageable()).map(inventoryConvertor::toDto);
        return PageResult.of(result.getContent(), result.getTotalElements());
    }

    @Operation(summary = "check inventories")
    @PostMapping("/commands/check")
    public void checkInventory() {
inventoryService.check();
    }

}
