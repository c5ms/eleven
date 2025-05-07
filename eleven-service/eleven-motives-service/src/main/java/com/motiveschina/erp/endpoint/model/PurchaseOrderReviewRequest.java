package com.motiveschina.erp.endpoint.model;

import com.motiveschina.core.layer.HttpRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PurchaseOrderReviewRequest implements HttpRequest {
    @NotNull
    private Boolean pass;
}
