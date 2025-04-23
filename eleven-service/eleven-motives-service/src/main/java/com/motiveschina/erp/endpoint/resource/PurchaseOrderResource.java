package com.motiveschina.erp.endpoint.resource;

import com.eleven.framework.web.ResourceId;
import com.eleven.framework.web.annonation.AsRestApi;
import com.eleven.framework.web.model.PageResult;
import com.eleven.framework.web.model.Pagination;
import com.motiveschina.erp.application.PurchaseOrderFinder;
import com.motiveschina.erp.application.PurchaseService;
import com.motiveschina.erp.application.command.*;
import com.motiveschina.erp.application.convertor.PurchaseConvertor;
import com.motiveschina.erp.application.dto.PurchaseOrderDto;
import com.motiveschina.erp.endpoint.request.PurchaseOrderCreateRequest;
import com.motiveschina.erp.endpoint.request.PurchaseOrderQuery;
import com.motiveschina.erp.endpoint.request.PurchaseOrderReviewRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Tag(name = "purchase-order")
@AsRestApi
@RequiredArgsConstructor
@RequestMapping("/purchase-orders")
public class PurchaseOrderResource {
    private final PurchaseService purchaseService;
    private final PurchaseConvertor purchaseConvertor;
    private final PurchaseOrderFinder purchaseOrderFinder;

    @Operation(summary = "get a purchase order")
    @GetMapping("/{id}")
    public Optional<PurchaseOrderDto> getPurchaseOrder(@PathVariable("id") ResourceId resourceId) {
        return purchaseOrderFinder.get(resourceId.asLong())
            .map(purchaseConvertor::toDto);
    }

    @Operation(summary = "query purchase orders")
    @GetMapping
    public PageResult<PurchaseOrderDto> listInventories(@ParameterObject PurchaseOrderQuery query,
                                                        @ParameterObject Pagination pagination) {
        var filter = PurchaseOrderFilter.builder()
            .status(query.getStatus())
            .build();
        var result = purchaseOrderFinder.queryPage(filter, pagination.toPageable()).map(purchaseConvertor::toDto);
        return PageResult.of(result.getContent(), result.getTotalElements());
    }


    @Operation(summary = "create a purchase order")
    @PostMapping
    public PurchaseOrderDto createPurchaseOrder(@RequestBody PurchaseOrderCreateRequest request) {
        var command = PurchaseOrderCreateCommand.builder()
            .supplierId(request.getSupplierId())
            .items(request.getItems())
            .build();
        var purchaseOrder = purchaseService.createPurchaseOrder(command);
        return purchaseConvertor.toDto(purchaseOrder);
    }

    @Operation(summary = "delete a purchase order")
    @PostMapping("/{id}")
    public void deletePurchaseOrder(@PathVariable("id") ResourceId resourceId) {
        var command = PurchaseOrderDeleteCommand.builder()
            .orderId(resourceId.asLong())
            .build();
        purchaseService.deletePurchaseOrder(command);
    }


    @Operation(summary = "submit a purchase order")
    @PostMapping("/{id}/commands/submit")
    public void submitPurchaseOrder(@PathVariable("id") ResourceId resourceId) {
        var command = PurchaseOrderSubmitCommand.builder()
            .orderId(resourceId.asLong())
            .build();
        purchaseService.submitPurchaseOrder(command);
    }

    @Operation(summary = "review a purchase order")
    @PostMapping("/{id}/commands/review")
    public void reviewPurchaseOrder(@PathVariable("id") ResourceId resourceId, @RequestBody PurchaseOrderReviewRequest request) {
        var command = PurchaseOrderReviewCommand.builder()
            .orderId(resourceId.asLong())
            .pass(request.getPass())
            .build();
        purchaseService.reviewPurchaseOrder(command);
    }

    @Operation(summary = "complete a purchase order")
    @PostMapping("/{id}/commands/complete")
    public void completePurchaseOrder(@PathVariable("id") ResourceId resourceId) {
        var command = PurchaseOrderCompleteCommand.builder()
            .orderId(resourceId.asLong())
            .stockDate(LocalDate.now())
            .build();
        purchaseService.completePurchaseOrder(command);
    }
}
