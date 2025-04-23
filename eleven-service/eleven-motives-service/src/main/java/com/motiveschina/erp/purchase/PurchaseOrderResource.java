package com.motiveschina.erp.purchase;

import java.util.Optional;
import com.eleven.framework.web.ResourceId;
import com.eleven.framework.web.annonation.AsRestApi;
import com.motiveschina.erp.purchase.command.PurchaseOrderCompleteCommand;
import com.motiveschina.erp.purchase.command.PurchaseOrderCreateCommand;
import com.motiveschina.erp.purchase.command.PurchaseOrderDeleteCommand;
import com.motiveschina.erp.purchase.command.PurchaseOrderReviewCommand;
import com.motiveschina.erp.purchase.command.PurchaseOrderSubmitCommand;
import com.motiveschina.erp.purchase.dto.PurchaseOrderDto;
import com.motiveschina.erp.purchase.request.PurchaseOrderCreateRequest;
import com.motiveschina.erp.purchase.request.PurchaseOrderReviewRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Tag(name = "purchase-orders")
@AsRestApi
@RequiredArgsConstructor
@RequestMapping("/purchase-orders")
public class PurchaseOrderResource {
    private final PurchaseService purchaseService;
    private final PurchaseConvertor purchaseConvertor;
    private final PurchaseOrderFinder purchaseOrderFinder;

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

    @Operation(summary = "get a purchase order")
    @GetMapping("/{id}")
    public Optional<PurchaseOrderDto> getPurchaseOrder(@PathVariable("id") ResourceId resourceId) {
        return purchaseOrderFinder.get(resourceId.asLong())
            .map(purchaseConvertor::toDto);
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
            .build();
        purchaseService.completePurchaseOrder(command);
    }
}
