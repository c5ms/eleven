package com.motiveschina.erp.endpoint.request;

import com.motiveschina.core.layer.HttpRequest;
import lombok.Data;

@Data
public class PurchaseOrderReviewRequest implements HttpRequest {
    private Boolean pass;
}
