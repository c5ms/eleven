package com.motiveschina.erp.endpoint.resource;

import java.time.LocalDate;
import java.util.Optional;
import com.eleven.framework.log.annonation.UseRequestLog;
import com.eleven.framework.utils.ImmutableValues;
import com.eleven.framework.web.ResourceId;
import com.eleven.framework.web.annonation.AsRestApi;
import com.eleven.framework.web.model.PageResult;
import com.eleven.framework.web.model.Pagination;
import com.motiveschina.erp.application.command.PurchaseOrderCompleteCommand;
import com.motiveschina.erp.application.command.PurchaseOrderCreateCommand;
import com.motiveschina.erp.application.command.PurchaseOrderDeleteCommand;
import com.motiveschina.erp.application.command.PurchaseOrderModifyCommand;
import com.motiveschina.erp.application.command.PurchaseOrderReviewCommand;
import com.motiveschina.erp.application.command.PurchaseOrderSubmitCommand;
import com.motiveschina.erp.application.convertor.PurchaseOrderConvertor;
import com.motiveschina.erp.application.model.PurchaseOrderDto;
import com.motiveschina.erp.application.service.PurchaseService;
import com.motiveschina.erp.domain.purchase.PurchaseOrderFilter;
import com.motiveschina.erp.domain.purchase.PurchaseOrderFinder;
import com.motiveschina.erp.endpoint.model.PurchaseOrderCreateRequest;
import com.motiveschina.erp.endpoint.model.PurchaseOrderModifyRequest;
import com.motiveschina.erp.endpoint.model.PurchaseOrderQuery;
import com.motiveschina.erp.endpoint.model.PurchaseOrderReviewRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Tag(name = "purchase-order")
@AsRestApi
@RequiredArgsConstructor
@RequestMapping("/purchase-orders")
public class PurchaseOrderResource {
    private final PurchaseService purchaseService;
    private final PurchaseOrderConvertor purchaseOrderConvertor;
    private final PurchaseOrderFinder purchaseOrderFinder;

    @Operation(summary = "get a purchase order")
    @GetMapping("/{id}")
    public Optional<PurchaseOrderDto> getPurchaseOrder(@PathVariable("id") ResourceId resourceId) {
        return purchaseOrderFinder.get(resourceId.asLong())
            .map(purchaseOrderConvertor::toDto);
    }

    @Operation(summary = "query purchase orders")
    @GetMapping
    public PageResult<PurchaseOrderDto> queryPurchaseOrders(@ParameterObject @Validated PurchaseOrderQuery query,
                                                            @ParameterObject @Validated Pagination pagination) {
        var filter = PurchaseOrderFilter.builder()
            .status(query.getStatus())
            .build();
        var result = purchaseOrderFinder.query(filter, pagination.toPageable()).map(purchaseOrderConvertor::toDto);
        return PageResult.of(result.getContent(), result.getTotalElements());
    }


    @UseRequestLog
    @Operation(summary = "create a purchase order")
    @PostMapping
    public PurchaseOrderDto createPurchaseOrder(@RequestBody @Validated PurchaseOrderCreateRequest request) {
        var command = PurchaseOrderCreateCommand.builder()
            .supplierId(request.getSupplierId())
            .items(ImmutableValues.of(request.getItems()))
            .build();
        var purchaseOrder = purchaseService.createPurchaseOrder(command);
        return purchaseOrderConvertor.toDto(purchaseOrder);
    }

    @Operation(summary = "update a purchase order")
    @PostMapping("/{orderId}")
    public PurchaseOrderDto updatePurchaseOrder(@PathVariable("orderId") ResourceId orderId,
                                                @RequestBody @Validated PurchaseOrderModifyRequest request) {
        var command = PurchaseOrderModifyCommand.builder()
            .orderId(orderId.asLong())
            .supplierId(request.getSupplierId())
            .items(ImmutableValues.of(request.getItems()))
            .build();
        purchaseService.updatePurchaseOrder(command);
        var order = purchaseOrderFinder.require(orderId.asLong());
        return purchaseOrderConvertor.toDto(order);
    }

    @Operation(summary = "delete a purchase order")
    @DeleteMapping("/{id}")
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
    public void reviewPurchaseOrder(@PathVariable("id") ResourceId resourceId,
                                    @RequestBody @Validated  PurchaseOrderReviewRequest request) {
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
            .transportationCost(340d)
            .build();
        purchaseService.completePurchaseOrder(command);
    }
}
